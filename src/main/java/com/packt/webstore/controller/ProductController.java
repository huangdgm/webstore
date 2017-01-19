package com.packt.webstore.controller;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.packt.webstore.domain.Product;
import com.packt.webstore.exception.NoProductsFoundUnderCategoryException;
import com.packt.webstore.exception.ProductNotFoundException;
import com.packt.webstore.service.ProductService;

@Controller
@RequestMapping("/products")
public class ProductController {

	@Autowired
	private ProductService productService;

	@RequestMapping
	public String list(Model model) {
		model.addAttribute("products", productService.getAllProducts());

		return "products";
	}

	@RequestMapping("/all")
	public String allProducts(Model model) {
		model.addAttribute("products", productService.getAllProducts());

		return "products";
	}

	@RequestMapping("/category/{category}")
	public String getProductsByCategory(Model model, @PathVariable("category") String productCategory) {
		List<Product> products = productService.getProductsByCategory(productCategory);

		if (products == null || products.isEmpty()) {
			throw new NoProductsFoundUnderCategoryException();
		}

		model.addAttribute("products", products);

		return "products";
	}

	@RequestMapping("/manufacturer/{manufacturer}")
	public String getProductsByManufacturer(Model model, @PathVariable String manufacturer) {
		model.addAttribute("products", productService.getProductsByManufacturer(manufacturer));

		return "products";
	}

	@RequestMapping("/filter/{ByCriteria}/{BySpecification}")
	public String getProductsByFilter(@MatrixVariable(pathVar = "ByCriteria") Map<String, List<String>> criteriaFilter,
			@MatrixVariable(pathVar = "BySpecification") Map<String, List<String>> specFilter, Model model) {
		// model.addAttribute("products",
		// productService.getProductsByFilter(criteriaFilter));
		// Why no products match under
		// http://localhost:8080/webstore/products/filter/ByCriteria;category=laptop,tablet/BySpecification;brand=google,dell
		model.addAttribute("products", productService.getProductsByFilter(specFilter));

		return "products";
	}

	@RequestMapping(value = "/product", method = RequestMethod.GET)
	public String getProductById(@RequestParam("id") String productId, Model model) {

		model.addAttribute("product", productService.getProductById(productId));

		return "product";
	}

	/*
	 * A URL can have multiple matrix variables; each matrix variable will be
	 * separated with a ; (semicolon). To assign multiple values to a single
	 * variable, each value must be separated by a ¡°,¡± (comma).
	 * 
	 * http://localhost:8080/webstore/products/filter/category/tablet/price;low=100
	 * ;high=1000?manufacturer=google
	 * 
	 * Be careful:
	 * 
	 * 1. The key=value pairs are separated by semicolon. 2. The value(s) are
	 * evaluated as a collection of string. So we have to deal with the value
	 * specified in the URL as the collection, even there is only one value
	 * specified. In this example, low=100, the key is "low", the value is a
	 * collection, which only contains a single element - "100".
	 */
	@RequestMapping("/filter/category/{category}/{price}")
	public String filterProducts(@MatrixVariable(pathVar = "price") Map<String, List<String>> priceFilter,
			@RequestParam("manufacturer") String manufacturer, @PathVariable("category") String category, Model model) {
		Set<Product> products = new HashSet<Product>();

		products.addAll(productService.getProductsByCategory(category));
		products.retainAll(productService.getProductsByManufacturer(manufacturer));
		products.retainAll(productService.getProductsByPriceRange(priceFilter));

		model.addAttribute("products", products);

		return "products";
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String getAddNewProductForm(Model model) {
		Product newProduct = new Product(); // form-backing bean

		model.addAttribute("newProduct", newProduct);

		return "addProduct";
	}

	// Alternatively, the following code sinppet can achieve the same purpose as
	// the above getAddNewProductForm method.
	// @RequestMapping(value = "/add", method = RequestMethod.GET)
	// public String getAddNewProductForm(@ModelAttribute("newProduct") Product
	// newProduct) {
	// return "addProduct";
	// }

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String processAddNewProductForm(@ModelAttribute("newProduct") Product newProduct, BindingResult result,
			HttpServletRequest request) {
		String[] suppressedFields = result.getSuppressedFields();

		if (suppressedFields.length > 0) {
			throw new RuntimeException("Attempting to bind disallowed fields: " + StringUtils.arrayToCommaDelimitedString(suppressedFields));
		}

		MultipartFile productImage = newProduct.getProductImage();
		MultipartFile productManual = newProduct.getProductManual();
		String rootDirectory = request.getSession().getServletContext().getRealPath("/");

		if (productImage != null && !productImage.isEmpty()) {
			try {
				productImage.transferTo(new File(rootDirectory + "resources\\images\\" + newProduct.getProductId() + ".png"));
			} catch (Exception e) {
				throw new RuntimeException("Product Image saving failed", e);
			}
		}

		if (productManual != null && !productManual.isEmpty()) {
			try {
				productManual.transferTo(new File(rootDirectory + "resources\\pdf\\" + newProduct.getProductId() + ".pdf"));
			} catch (Exception e) {
				throw new RuntimeException("Product Manual saving failed", e);
			}
		}

		productService.addProduct(newProduct);

		return "redirect:/products";
	}

	@RequestMapping("/invalidPromoCode")
	public String invalidPromoCode() {
		return "invalidPromoCode";
	}

	@ExceptionHandler(ProductNotFoundException.class)
	public ModelAndView handleError(HttpServletRequest req, ProductNotFoundException exception) {
		ModelAndView mav = new ModelAndView();

		mav.addObject("invalidProductId", exception.getProductId());
		mav.addObject("exception", exception);
		mav.addObject("url", req.getRequestURL() + "?" + req.getQueryString());

		mav.setViewName("productNotFound");

		return mav;
	}

	@InitBinder
	public void initialiseBinder(WebDataBinder binder) {
		binder.setDisallowedFields("unitsInOrder", "discontinued");
		binder.setAllowedFields("productId", "name", "unitPrice", "description", "manufacturer", "category", "unitsInStock",
				"productImage", "condition", "productManual", "language");
	}
}
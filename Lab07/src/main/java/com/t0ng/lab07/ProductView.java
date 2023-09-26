package com.t0ng.lab07;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

@Route()
public class ProductView extends VerticalLayout {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    private String selectedProductId = "";
    private ComboBox productList = new ComboBox("Product List");
    private TextField productName = new TextField("Product Name");
    private NumberField productCost = new NumberField("Product Cost");
    private NumberField productProfit = new NumberField("Product Profit");
    private NumberField productPrice = new NumberField("Product Price");
    private HorizontalLayout buttonLayout = new HorizontalLayout();
    private Button addProduct = new Button("Add Product");
    private Button updateProduct = new Button("Update Product");
    private Button deleteProduct = new Button("Delete Product");
    private Button clearProduct = new Button("Clear Product");

    public double calculatePrice(double cost, double profit) {
        Double price = WebClient.create()
                .get()
                .uri("http://localhost:8080/getPrice/" + cost + "/" + profit)
                .retrieve()
                .bodyToMono(Double.class)
                .block();
        return price;
    }

    public void updateProductList() {
        List<Product> products = (List<Product>) rabbitTemplate.convertSendAndReceive("ProductExchange","getall", "");
        List<String> productNames = new ArrayList<>();
        for (Product product : products) {
            productNames.add(product.getProductName());
        }
        productList.setItems(productNames);
    }

    public ProductView() {
        productList.setWidth("600px");
        productName.setWidth("600px");
        productName.setValue("");
        productCost.setWidth("600px");
        productCost.setValue(0.0);
        productProfit.setWidth("600px");
        productProfit.setValue(0.0);
        productPrice.setWidth("600px");
        productPrice.setValue(0.0);
        productPrice.setEnabled(false);
        buttonLayout.add(addProduct, updateProduct, deleteProduct, clearProduct);

        this.add(productList);
        this.add(productName);
        this.add(productCost);
        this.add(productProfit);
        this.add(productPrice);
        this.add(buttonLayout);

        productList.addFocusListener(e -> {
            updateProductList();
        });

        productList.addValueChangeListener(e -> {
            if (productList.getValue() == null) {
                selectedProductId = "";
                productName.setValue("");
                productCost.setValue(0.0);
                productProfit.setValue(0.0);
                productPrice.setValue(0.0);
                return;
            };
            Product product = (Product) rabbitTemplate.convertSendAndReceive("ProductExchange","getname", productList.getValue());
            selectedProductId = product.get_id();
            productName.setValue(product.getProductName());
            productCost.setValue(product.getProductCost());
            productProfit.setValue(product.getProductProfit());
            productPrice.setValue(product.getProductPrice());
        });

        productCost.addKeyPressListener(Key.ENTER, e -> {
            productPrice.setValue(calculatePrice(productCost.getValue(), productProfit.getValue()));
        });

        productProfit.addKeyPressListener(Key.ENTER, e -> {
            productPrice.setValue(calculatePrice(productCost.getValue(), productProfit.getValue()));
        });

        addProduct.addClickListener(e -> {
            productPrice.setValue(calculatePrice(productCost.getValue(), productProfit.getValue()));
            Product product = new Product();
            product.setProductName(productName.getValue());
            product.setProductCost(productCost.getValue());
            product.setProductProfit(productProfit.getValue());
            product.setProductPrice(productPrice.getValue());
            boolean result = (boolean) rabbitTemplate.convertSendAndReceive("ProductExchange","add", product);
            if (result) {
                Notification.show("Add Product Success", 500, Notification.Position.BOTTOM_START);
                updateProductList();
            }
        });

        updateProduct.addClickListener(e -> {
            productPrice.setValue(calculatePrice(productCost.getValue(), productProfit.getValue()));
            Product product = new Product();
            product.set_id(selectedProductId);
            product.setProductName(productName.getValue());
            product.setProductCost(productCost.getValue());
            product.setProductProfit(productProfit.getValue());
            product.setProductPrice(productPrice.getValue());
            boolean result = (boolean) rabbitTemplate.convertSendAndReceive("ProductExchange","update", product);
            if (result) {
                Notification.show("Update Product Success", 500, Notification.Position.BOTTOM_START);
                updateProductList();
            }
        });

        deleteProduct.addClickListener(e -> {
            boolean result = (boolean) rabbitTemplate.convertSendAndReceive("ProductExchange","delete", productName.getValue());
            if (result) {
                Notification.show("Delete Product Success");
                productName.setValue("");
                productCost.setValue(0.0);
                productProfit.setValue(0.0);
                productPrice.setValue(0.0);
                updateProductList();
            }
        });

        clearProduct.addClickListener(e -> {
            productList.setValue(null);
            productName.setValue("");
            productCost.setValue(0.0);
            productProfit.setValue(0.0);
            productPrice.setValue(0.0);
            Notification.show("Clear Product Success", 500, Notification.Position.BOTTOM_START);
        });
    }
}
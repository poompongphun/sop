package com.example.lab72.view;

import com.example.lab72.pojo.Product;
import com.example.lab72.pojo.Products;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

@Route("")
public class ProductView extends VerticalLayout {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    private ComboBox pdList;
    private TextField pdName;
    private NumberField pdCost, pdProfit, pdPrice;

    private Button addBtn, updateBtn, deleteBtn, clearBtn;

    private Products allPd;

    public ProductView() {


        this.pdList = new ComboBox<>("Product List");
        this.pdList.setWidth("600px");

        this.pdName = new TextField("Product Name");
        this.pdName.setWidth("600px");
        this.pdName.setValue("");

        this.pdCost = new NumberField("Product Cost");
        this.pdCost.setWidth("600px");
        this.pdCost.setValue(0.0);

        this.pdProfit = new NumberField("Product Profit");
        this.pdProfit.setWidth("600px");
        this.pdProfit.setValue(0.0);

        this.pdPrice = new NumberField("Product Price");
        this.pdPrice.setWidth("600px");
        this.pdPrice.setValue(0.0);
        this.pdPrice.setEnabled(false);

        this.addBtn = new Button("Add Product");
        this.updateBtn = new Button("Update Product");
        this.deleteBtn = new Button("Delete Product");
        this.clearBtn = new Button("Clear Product");

        this.pdList.addFocusListener(focusEvent -> {
            this.allPd = (Products) rabbitTemplate.convertSendAndReceive("ProductExchange", "getall", "");
            for (int i = 0; i < this.allPd.products.size(); i++) {
                this.pdList.setItems(this.allPd.products.get(i).getProductName());
            }
        });

        this.pdList.addValueChangeListener(valueChangeEvent -> {
            Product pd = (Product) rabbitTemplate.convertSendAndReceive("ProductExchange", "getname", this.pdList.getValue());
            this.setShow(pd);
        });

        this.addBtn.addClickListener(buttonClickEvent -> {

        });


        add(this.pdList, this.pdName, this.pdCost, this.pdProfit, this.pdPrice, new HorizontalLayout(this.addBtn, this.updateBtn, this.deleteBtn, this.clearBtn));
    }

    public void setShow(Product pd) {
        this.pdName.setValue(pd.getProductName());
        this.pdCost.setValue(pd.getProductCost());
        this.pdProfit.setValue(pd.getProductProfit());
        this.pdPrice.setValue(pd.getProductPrice());
    }
}

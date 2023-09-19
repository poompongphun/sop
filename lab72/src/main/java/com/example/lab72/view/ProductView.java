package com.example.lab72.view;

import com.example.lab72.pojo.Product;
import com.example.lab72.pojo.Products;
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

@Route("")
public class ProductView extends VerticalLayout {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    private ComboBox pdList;
    private TextField pdName;
    private NumberField pdCost, pdProfit, pdPrice;

    private Button addBtn, updateBtn, deleteBtn, clearBtn;
    private Notification nf;

    private Products allPd;
    private ArrayList<String> textList;
    private int showingIndex = -1;

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

        this.nf = new Notification();
        this.nf.setDuration(1500);

        this.pdCost.addKeyDownListener(keyDownEvent -> {
            if (keyDownEvent.getKey().toString().equals("Enter")) {
                this.calcProductPrice();
            }
        });

        this.pdProfit.addKeyDownListener(keyDownEvent -> {;
            if (keyDownEvent.getKey().toString().equals("Enter")) {
                this.calcProductPrice();
            }
        });

        this.pdList.addFocusListener(focusEvent -> {
                System.out.println("Check");
                this.allPd = (Products) rabbitTemplate.convertSendAndReceive("ProductExchange", "getall", "");
                this.textList = new ArrayList<String>();
                System.out.println(textList);
                for (int i = 0; i < this.allPd.products.size(); i++) {
//                this.pdList.setItems(this.allPd.products.get(i).getProductName());
                    textList.add(this.allPd.products.get(i).getProductName());
                }
                System.out.println(textList);
                this.pdList.setItems(this.textList);
        });

        this.pdList.addValueChangeListener(valueChangeEvent -> {
            this.showingIndex = this.textList.indexOf(this.pdList.getValue());
            Product pd = (Product) rabbitTemplate.convertSendAndReceive("ProductExchange", "getname", this.pdList.getValue());
            this.setShow(pd);
        });

        this.addBtn.addClickListener(buttonClickEvent -> {
            this.calcProductPrice();
            Product newPd = new Product(null, this.pdName.getValue(), this.pdCost.getValue(), this.pdProfit.getValue(), this.pdPrice.getValue());
            rabbitTemplate.convertSendAndReceive("ProductExchange", "add", newPd);
            this.nf.setText("Added");
            this.nf.open();
        });

        this.updateBtn.addClickListener(buttonClickEvent -> {
            this.calcProductPrice();
            Product newPd = new Product(this.allPd.products.get(this.showingIndex).get_id(), this.pdName.getValue(), this.pdCost.getValue(), this.pdProfit.getValue(), this.pdPrice.getValue());
            rabbitTemplate.convertSendAndReceive("ProductExchange", "update", newPd);
            this.nf.setText("Updated");
            this.nf.open();
        });

        this.deleteBtn.addClickListener(buttonClickEvent -> {
            rabbitTemplate.convertSendAndReceive("ProductExchange", "delete", this.allPd.products.get(this.showingIndex));
            this.nf.setText("Deleted");
            this.nf.open();
        });

        this.clearBtn.addClickListener(buttonClickEvent -> {
            this.pdName.setValue("");
            this.pdCost.setValue(0.0);
            this.pdProfit.setValue(0.0);
            this.pdPrice.setValue(0.0);
            this.pdList.clear();
        });


        add(this.pdList, this.pdName, this.pdCost, this.pdProfit, this.pdPrice, new HorizontalLayout(this.addBtn, this.updateBtn, this.deleteBtn, this.clearBtn));
    }

    public void setShow(int index) {
        Product pd = this.allPd.products.get(index);
        this.pdName.setValue(pd.getProductName());
        this.pdCost.setValue(pd.getProductCost());
        this.pdProfit.setValue(pd.getProductProfit());
        this.pdPrice.setValue(pd.getProductPrice());
    }

    public void setShow(Product pd) {
        this.pdName.setValue(pd.getProductName());
        this.pdCost.setValue(pd.getProductCost());
        this.pdProfit.setValue(pd.getProductProfit());
        this.pdPrice.setValue(pd.getProductPrice());
    }

    public void calcProductPrice() {
        this.pdPrice.setValue(WebClient.create()
                .get()
                .uri("http://localhost:8080/getPrice/" + this.pdCost.getValue() + "/" + this.pdProfit.getValue())
                .retrieve()
                .bodyToMono(double.class)
                .block());
    }
}

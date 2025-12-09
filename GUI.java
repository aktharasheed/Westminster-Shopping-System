import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.util.List;

public class GUI extends JFrame {
    private ShoppingCart shoppingCart = new ShoppingCart();
    private JPanel productDetailsPanel;
    private JButton addToShoppingCartButton, shoppingCartButton;
    private JComboBox<String> categoryComboBox;
    private JLabel label1;
    private JTable table;
    private DefaultTableModel model;
    private List<Product> products;
    private JFrame cartFrame;

    public GUI(WestminsterShoppingManager shoppingManager) {
        this.products = shoppingManager.getProducts();
        initComponents();
        addListeners();
        displayAllProducts(); // Display all products initially
    }

    private void initComponents() {
        setTitle("Westminster Shopping Center");
        setSize(700, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());

        shoppingCartButton = new JButton("Shopping Cart");
        shoppingCartButton.addActionListener(e -> displayShoppingCart());

        label1 = new JLabel("Select Product Category ");
        label1.setHorizontalAlignment(JLabel.CENTER);
        label1.setVerticalAlignment(JLabel.TOP);

        String[] categories = {"All", "Electronics", "Clothes"};
        categoryComboBox = new JComboBox<>(categories);
        categoryComboBox.setSelectedIndex(0); // Set "All" as the default selection

        String[] columnNames = {"Product ID", "Product Name", "Category", "Price", "Info"};
        model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make all cells non-editable
            }
        };
        table = new JTable(model);

        // Set selection mode to single selection
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(500, 200));

        JPanel labelAndComboBoxPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        labelAndComboBoxPanel.add(label1);
        labelAndComboBoxPanel.add(categoryComboBox);

        productDetailsPanel = new JPanel();
        productDetailsPanel.setLayout(new BoxLayout(productDetailsPanel, BoxLayout.Y_AXIS));

        addToShoppingCartButton = new JButton("Add to Shopping Cart");

        JPanel panel1 = new JPanel();
        panel1.setLayout(new FlowLayout());
        panel1.add(addToShoppingCartButton);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.NORTHEAST;
        add(shoppingCartButton, gbc);

        gbc.gridy = 1;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.NORTH;
        add(labelAndComboBoxPanel, gbc);

        gbc.gridy = 2;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        add(scrollPane, gbc);

        gbc.gridy = 6;
        gbc.weighty = 3;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.SOUTH;
        add(panel1, gbc);

        gbc.gridy = 7;
        gbc.weighty = 3;
        gbc.fill = GridBagConstraints.BOTH;
        add(productDetailsPanel, gbc);

        // Add the table selection listener
        table.getSelectionModel().addListSelectionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                displayProductDetails(row);
            }
        });

        setVisible(true);
    }

    private void addListeners() {
        categoryComboBox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                String category = (String) e.getItem();
                switch (category) {
                    case "All" -> displayAllProducts();
                    case "Electronics" -> displayElectronics();
                    case "Clothes" -> displayClothes();
                }
            }
        });

        table.getSelectionModel().addListSelectionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                displayProductDetails(row);
            }
        });

        addToShoppingCartButton.addActionListener(e -> addToShoppingCart());
        shoppingCartButton.addActionListener(e -> displayShoppingCart());
    }

    private void displayProductDetails(int row) {
        // Clear existing components in productDetailsPanel
        productDetailsPanel.removeAll();
        productDetailsPanel.revalidate();
        productDetailsPanel.repaint();

        if (row != -1) {
            String productID = (String) table.getValueAt(row, 0);
            String productName = (String) table.getValueAt(row, 1);
            String category = (String) table.getValueAt(row, 2);
            int price = (int) table.getValueAt(row, 3);
            String details = (String) table.getValueAt(row, 4);

            JLabel idLabel = new JLabel("Product ID: " + productID);
            JLabel nameLabel = new JLabel("Product Name: " + productName);
            JLabel categoryLabel = new JLabel("Category: " + category);
            JLabel priceLabel = new JLabel("Price: $" + price);
            JLabel detailsLabel = new JLabel("Details: " + details);

            // Add the labels to productDetailsPanel
            productDetailsPanel.add(idLabel);
            productDetailsPanel.add(nameLabel);
            productDetailsPanel.add(categoryLabel);
            productDetailsPanel.add(priceLabel);
            productDetailsPanel.add(detailsLabel);

            // Refresh the layout
            productDetailsPanel.revalidate();
            productDetailsPanel.repaint();
        }
    }

    private void addToShoppingCart() {
        int selectedRow = table.getSelectedRow();

        if (selectedRow != -1) {
            Product selectedProduct = products.get(selectedRow);

            String productID = selectedProduct.getProduct_id();
            String productName = selectedProduct.getProduct_name();
            int numberOfAvailableItems = 0; // Modify this based on your logic
            int price = selectedProduct.getPrice();

            if (selectedProduct instanceof Electronics) {
                Electronics electronicsProduct = (Electronics) selectedProduct;
                String brand = electronicsProduct.getBrand();
                int warrantyPeriod = electronicsProduct.getWarranty();
                shoppingCart.addProduct(new Electronics(productID, productName, numberOfAvailableItems, price, brand, warrantyPeriod));
            } else if (selectedProduct instanceof Clothing) {
                Clothing clothingProduct = (Clothing) selectedProduct;
                String size = clothingProduct.getSize();
                String color = clothingProduct.getColour();
                shoppingCart.addProduct(new Clothing(productID, productName, numberOfAvailableItems, price, size, color));
            } else {
                // Handle other product types if needed
                return;
            }

            // Display a confirmation message or update the UI as needed
            JOptionPane.showMessageDialog(this, "Product added to shopping cart!");
        }
    }



    private void displayAllProducts() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        for (Product product : products) {
            String category = (product instanceof Electronics) ? "Electronics" : "Clothing";
            String details = getProductDetails(product);
            Object[] rowData = {product.getProduct_id(), product.getProduct_name(), category, product.getPrice(), details};
            model.addRow(rowData);
        }
    }

    private void displayElectronics() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        for (Product product : products) {
            if (product instanceof Electronics) {
                String details = getProductDetails(product);
                Object[] rowData = {product.getProduct_id(), product.getProduct_name(), "Electronics", product.getPrice(), details};
                model.addRow(rowData);
            }
        }
    }

    private void displayClothes() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        for (Product product : products) {
            if (product instanceof Clothing) {
                String details = getProductDetails(product);
                Object[] rowData = {product.getProduct_id(), product.getProduct_name(), "Clothing", product.getPrice(), details};
                model.addRow(rowData);
            }
        }
    }

    private String getProductDetails(Product product) {
        if (product instanceof Electronics) {
            Electronics electronics = (Electronics) product;
            return "Brand: " + electronics.getBrand() + ", Warranty Period: " + electronics.getWarranty();
        } else if (product instanceof Clothing) {
            Clothing clothing = (Clothing) product;
            return "Size: " + clothing.getSize() + ", Color: " + clothing.getColour();
        }
        return "";
    }

    private void displayShoppingCart() {
        if (cartFrame == null) {
            // Create the cartFrame only if it doesn't exist
            cartFrame = new JFrame("Shopping Cart");
            cartFrame.setSize(700, 600);
            cartFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            cartFrame.setLayout(new BorderLayout());

            String[] columnNames1 = {"Product", "Quantity", "Price"};
            DefaultTableModel cartModel = new DefaultTableModel(columnNames1, 0);
            JTable cartTable = new JTable(cartModel);

            JScrollPane scrollPane1 = new JScrollPane(cartTable);
            scrollPane1.setPreferredSize(new Dimension(500, 200));

            JPanel panel1 = new JPanel(new BorderLayout());
            panel1.add(scrollPane1, BorderLayout.PAGE_START);
            cartFrame.add(panel1);

            JPanel panel2 = new JPanel();
            JButton closeButton = new JButton("Close");
            closeButton.addActionListener(e -> {
                // Close the cartFrame
                cartFrame.dispose();
            });
            panel2.add(closeButton);
            cartFrame.add(panel2, BorderLayout.PAGE_END);
        }

        // Clear existing components in the cartFrame
        cartFrame.getContentPane().removeAll();

        // Populate the cart table with shoppingCart contents
        DefaultTableModel cartModel = new DefaultTableModel(new String[]{"Product", "Quantity", "Price"}, 0);
        JTable cartTable = new JTable(cartModel);

        double totalPrice = 0.0; // Variable to keep track of the total price

        for (Product product : shoppingCart.getProducts()) {
            Object[] rowData = { "Name: " + product.getProduct_name() +",  ID:" + product.getProduct_id(), 1, product.getPrice()};
            cartModel.addRow(rowData);

            // Update the total price
            totalPrice += product.getPrice();
        }

        // Display the total price
        JLabel totalPriceLabel = new JLabel("Total Price: $" + totalPrice);

        JScrollPane scrollPane1 = new JScrollPane(cartTable);
        scrollPane1.setPreferredSize(new Dimension(500, 200));

        JPanel panel1 = new JPanel(new BorderLayout());
        panel1.add(scrollPane1, BorderLayout.PAGE_START);
        panel1.add(totalPriceLabel, BorderLayout.PAGE_END);
        cartFrame.add(panel1);

        // Add the close button
        JPanel panel2 = new JPanel();
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> {
            // Close the cartFrame
            cartFrame.dispose();
        });
        panel2.add(closeButton);

        cartFrame.add(panel2, BorderLayout.PAGE_END);

        // Refresh the layout
        cartFrame.revalidate();
        cartFrame.repaint();

        // Make the cartFrame visible or bring it to the front
        cartFrame.setVisible(true);
        cartFrame.toFront();
    }

    public static void main(String[] arg) {
        SwingUtilities.invokeLater(() -> {
            WestminsterShoppingManager shoppingManager = new WestminsterShoppingManager();
            GUI gui = new GUI(shoppingManager);
        });
    }
}
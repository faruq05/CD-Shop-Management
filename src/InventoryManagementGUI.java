import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.GridLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.JTextPane;

public class InventoryManagementGUI {
	private JFrame frame;
	private JList<String> inventoryList;
	private DefaultListModel<String> inventoryListModel;
	private List<String> fileLines = new ArrayList<>();

	private JTextArea invoiceTextArea;
	private Inventory inventory;
	private Invoice currentInvoice;
	private JTextField searchField;
	private JButton addButton;
	private JButton removeButton;

	private List<StockableProduct> initialProducts = new ArrayList<>();
	private final String dataFilePath = "D:\\java\\Cse215Project\\initial_products.txt";

	/**
	 * @wbp.parser.entryPoint
	 */
	public InventoryManagementGUI() throws IOException {
		frame = new JFrame("Inventory Management System");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout());

		frame.setSize(1000, 500);

		inventory = new Inventory();
		currentInvoice = new Invoice();

		// Create some initial products and add them to the inventory
		loadInitialProducts(dataFilePath);

		inventoryListModel = new DefaultListModel<>();
		inventoryList = new JList<>(inventoryListModel);
		inventoryList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane inventoryScrollPane = new JScrollPane(inventoryList);
		inventoryScrollPane.setPreferredSize(new Dimension(600, 400));
		frame.getContentPane().add(inventoryScrollPane, BorderLayout.WEST);

		invoiceTextArea = new JTextArea(20, 40);
		invoiceTextArea.setEditable(false);
		invoiceTextArea.setFont(new Font("Arial", Font.PLAIN, 12));
		JScrollPane invoiceScrollPane = new JScrollPane(invoiceTextArea);
		invoiceScrollPane.setPreferredSize(new Dimension(400, 400));
		frame.getContentPane().add(invoiceScrollPane, BorderLayout.CENTER);

		JPanel buttonPanel = new JPanel();
		JButton musicButton = new JButton("Music");
		JButton movieButton = new JButton("Movie");
		JButton gameButton = new JButton("Game");
		JButton addButton = new JButton("Add to Invoice");
		JButton sortPriceButton = new JButton("Sort by Price");
		sortPriceButton.setPreferredSize(new Dimension(120, 50));
		JButton sortStockButton = new JButton("Sort by Stock");
		sortStockButton.setPreferredSize(new Dimension(120, 50));

		addButton.setPreferredSize(new Dimension(120, 50));
		JButton searchButton = new JButton("Search");
		searchButton.setPreferredSize(new Dimension(100, 50));

		musicButton.setPreferredSize(new Dimension(100, 50));
		movieButton.setPreferredSize(new Dimension(100, 50));

		gameButton.setPreferredSize(new Dimension(100, 50));

		buttonPanel.add(musicButton);
		buttonPanel.add(movieButton);
		buttonPanel.add(gameButton);
		buttonPanel.add(addButton);
		buttonPanel.add(searchButton);
		buttonPanel.add(sortPriceButton);
		buttonPanel.add(sortStockButton);
		frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);

		searchField = new JTextField(20);
		frame.getContentPane().add(searchField, BorderLayout.NORTH);
		searchField.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusGained(java.awt.event.FocusEvent evt) {
				if (searchField.getText().equals("Search products")) {
					searchField.setText("");
					searchField.setForeground(Color.BLACK);
				}
			}

			public void focusLost(java.awt.event.FocusEvent evt) {
				if (searchField.getText().equals("")) {
					searchField.setText("Search products");
					searchField.setForeground(Color.GRAY);
				}
			}
		});

		musicButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				populateInventoryList("Music");
				initializeMusicButtons();
				inventory.sortByPrice();
//				txtpnCdShopProject.setVisible(false);
//				movieButton.setVisible(false);
//				gameButton.setVisible(false);
//				// addButton.setVisible(false);
//				searchButton.setVisible(false);
//				musicButton.setVisible(false);

			}
		});

		movieButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				populateInventoryList("Movie");
				initializeMusicButtons();
				inventory.sortByPrice();
//				movieButton.setVisible(false);
//				gameButton.setVisible(false);
//				// addButton.setVisible(false);
//				searchButton.setVisible(false);
//				musicButton.setVisible(false);

			}
		});

		gameButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				populateInventoryList("Game");
				initializeMusicButtons();
				inventory.sortByPrice();
//				movieButton.setVisible(false);
//				gameButton.setVisible(false);
//				// addButton.setVisible(false);
//				searchButton.setVisible(false);
//				musicButton.setVisible(false);

			}
		});

		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String selectedItem = inventoryList.getSelectedValue();
				if (selectedItem != null) {
					int productId = Integer.parseInt(selectedItem.split(",")[0].substring(4).trim());
					StockableProduct product = inventory.getItem(productId);
					if (product != null) {
						// Check if there is enough stock to add to the invoice
						if (product.getStock() > 0) {
							// Decrease the stock by 1
							product.decreaseStock(1);

							// Add the product to the invoice
							currentInvoice.addProduct(product);

							// Update the displayed list with the new stock
							inventoryListModel.removeElement(selectedItem);
							inventoryListModel.addElement(product.getInfo());

							updateInvoiceTextArea();
						} else {
							JOptionPane.showMessageDialog(frame, "Product is out of stock.", "Error",
									JOptionPane.ERROR_MESSAGE);
						}
					}
				}
			}
		});

		searchButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String searchTerm = searchField.getText().toLowerCase();
				searchProducts(searchTerm);
			}
		});

//		sort by price 
		sortPriceButton.addActionListener(e -> {
			inventory.sortByPrice(); // Sort inventory by price
			updateInventoryList(); // Update the inventory list with the sorted data
		});

//		sort by stock
		sortStockButton.addActionListener(e -> {
			inventory.sortByAvailableStock(); // Sort inventory by available stock
			updateInventoryList(); // Update the inventory list with the sorted data
		});

		frame.setVisible(true);
	}

//	update inventory after sorting 
	private void updateInventoryList() {
		inventoryListModel.clear();
		for (StockableProduct product : inventory.getItems()) {
			inventoryListModel.addElement(product.getInfo());
		}

	}

	public void populateInventoryList(String category) {
		inventoryListModel.clear();
		for (StockableProduct product : initialProducts) {
			if (product.getCategory().equals(category)) {
				inventoryListModel.addElement(product.getInfo());
			}
		}
	}

	public void searchProducts(String searchTerm) {
		inventoryListModel.clear();
		for (StockableProduct product : initialProducts) {
			if (product instanceof Music) {
				Music music = (Music) product;
				if (music.getArtist().toLowerCase().contains(searchTerm)
						|| music.getName().toLowerCase().contains(searchTerm)
						|| String.valueOf(music.getPrice()).contains(searchTerm)) {
					inventoryListModel.addElement(music.getInfo());
				}
			} else if (product instanceof Game) {
				Game game = (Game) product;
				if (game.getDeveloper().toLowerCase().contains(searchTerm)
						|| game.getName().toLowerCase().contains(searchTerm)
						|| String.valueOf(game.getPrice()).contains(searchTerm)) {
					inventoryListModel.addElement(game.getInfo());
				}
			} else if (product instanceof Movei) {
				Movei movie = (Movei) product;
				if (movie.getDirector().toLowerCase().contains(searchTerm)
						|| movie.getName().toLowerCase().contains(searchTerm)
						|| String.valueOf(movie.getPrice()).contains(searchTerm)) {
					inventoryListModel.addElement(movie.getInfo());
				}
			}
		}
	}

	public void updateInvoiceTextArea() {
		invoiceTextArea.setText(currentInvoice.getInvoice());
	}

	private void initializeMusicButtons() {
		addButton = new JButton("Add");
		removeButton = new JButton("Remove");

		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Create a panel to hold the input fields
				JPanel inputPanel = new JPanel(new GridLayout(7, 2));
				JTextField idField = new JTextField(5);
				JTextField nameField = new JTextField(20);
				JTextField priceField = new JTextField(10);
				JTextField categoryField = new JTextField(10);
				JTextField yearPublishedField = new JTextField(5);
				JTextField stockField = new JTextField(5);
				JTextField additionalInfoField = new JTextField(20);

				// Add labels and fields to the input panel
				inputPanel.add(new JLabel("ID:"));
				inputPanel.add(idField);
				inputPanel.add(new JLabel("Name:"));
				inputPanel.add(nameField);
				inputPanel.add(new JLabel("Price:"));
				inputPanel.add(priceField);
				inputPanel.add(new JLabel("Category:"));
				inputPanel.add(categoryField);
				inputPanel.add(new JLabel("yearPublished:"));
				inputPanel.add(yearPublishedField);
				inputPanel.add(new JLabel("Stock:"));
				inputPanel.add(stockField);
				inputPanel.add(new JLabel("Artist / Developer / Director:"));
				inputPanel.add(additionalInfoField);

				// Show the input dialog with the input panel
				int result = JOptionPane.showConfirmDialog(frame, inputPanel, "Enter Product Details",
						JOptionPane.OK_CANCEL_OPTION);

				if (result == JOptionPane.OK_OPTION) {
					// Get values from input fields
					String idStr = idField.getText();
					String name = nameField.getText();
					String priceStr = priceField.getText();
					String category = categoryField.getText();
					String yearPublishedStr = yearPublishedField.getText();
					String stockStr = stockField.getText();
					String additionalInfo = additionalInfoField.getText();

					// Check if any input is missing
					if (idStr.isEmpty() || name.isEmpty() || priceStr.isEmpty() || category.isEmpty()
							|| yearPublishedStr.isEmpty() || stockStr.isEmpty() || additionalInfo.isEmpty()) {
						JOptionPane.showMessageDialog(frame, "Please fill in all fields.", "Error",
								JOptionPane.ERROR_MESSAGE);
						return;
					}

					try {
						// Parse the input values
						int id = Integer.parseInt(idStr);
						double price = Double.parseDouble(priceStr);
						int yearPublished = Integer.parseInt(yearPublishedStr);
						int stock = Integer.parseInt(stockStr);

						// Create a new product
						StockableProduct product = null;
						switch (category) {
						case "Music":
							product = new Music(id, name, price, category, stock, yearPublished, additionalInfo);
							break;
						case "Movie":
							product = new Movei(id, name, price, category, stock, yearPublished, additionalInfo);
							break;
						case "Game":
							product = new Game(id, name, price, category, stock, yearPublished, additionalInfo);
							break;
						default:
							break;
						}

						if (product != null) {
							initialProducts.add(product);
							inventory.addItem(product);
							inventoryListModel.addElement(product.getInfo());
							saveDataToFile(dataFilePath);
						}
					} catch (NumberFormatException ex) {
						JOptionPane.showMessageDialog(frame, "Invalid input for ID, price, yearPublished, or stock.",
								"Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});

		// Remove Button for all product types
		removeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String selectedItem = inventoryList.getSelectedValue();
				if (selectedItem != null) {
					int productId = Integer.parseInt(selectedItem.split(",")[0].substring(4).trim());
					StockableProduct product = inventory.getItem(productId);
					if (product != null) {
						if (product instanceof Music) {
							currentInvoice.removeProduct(product);
						} else if (product instanceof Movei) {
							currentInvoice.removeProduct(product);
						} else if (product instanceof Game) {
							currentInvoice.removeProduct(product);
						}
						// Remove from the displayed list
						initialProducts.remove(product);
						inventoryListModel.removeElement(selectedItem);
						currentInvoice.removeProduct(product);
						removeItemAndUpdateFile(productId);

						// Remove the product from the text file
						saveDataToFile(dataFilePath);
						updateInvoiceTextArea();
					}
				}
			}
		});

		// Add the buttons to the button panel
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(addButton);
		buttonPanel.add(removeButton);
		frame.getContentPane().add(buttonPanel, BorderLayout.EAST);

		// Call revalidate and repaint to update the frame
		frame.revalidate();
		frame.repaint();
	}

	public void updateInvoiceTextAreaa() {
		invoiceTextArea.setText(currentInvoice.getInvoice());
	}

	private void initializeMusicButtonss() {
		addButton = new JButton("Add");
		removeButton = new JButton("Remove");

		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Create a panel to hold the input fields
				JPanel inputPanel = new JPanel(new GridLayout(6, 2));
				JTextField idField = new JTextField(5);
				JTextField nameField = new JTextField(20);
				JTextField priceField = new JTextField(10);
				JTextField categoryField = new JTextField(10);
				JTextField yearPublishedField = new JTextField(5);
				JTextField stockField = new JTextField(5);

				// Add labels and fields to the input panel
				inputPanel.add(new JLabel("ID:"));
				inputPanel.add(idField);
				inputPanel.add(new JLabel("Name:"));
				inputPanel.add(nameField);
				inputPanel.add(new JLabel("Price:"));
				inputPanel.add(priceField);
				inputPanel.add(new JLabel("Category:"));
				inputPanel.add(categoryField);
				inputPanel.add(new JLabel("yearPublished:"));
				inputPanel.add(yearPublishedField);
				inputPanel.add(new JLabel("Stock:"));
				inputPanel.add(stockField);

				// Show the input dialog with the input panel
				int result = JOptionPane.showConfirmDialog(frame, inputPanel, "Enter Product Details",
						JOptionPane.OK_CANCEL_OPTION);

				if (result == JOptionPane.OK_OPTION) {
					// Get values from input fields
					String idStr = idField.getText();
					String name = nameField.getText();
					String priceStr = priceField.getText();
					String category = categoryField.getText();
					String yearPublishedStr = yearPublishedField.getText();
					String stockStr = stockField.getText();

					// Check if any input is missing
					if (idStr.isEmpty() || name.isEmpty() || priceStr.isEmpty() || category.isEmpty()
							|| yearPublishedStr.isEmpty() || stockStr.isEmpty()) {
						JOptionPane.showMessageDialog(frame, "Please fill in all fields.", "Error",
								JOptionPane.ERROR_MESSAGE);
						return;
					}

					try {
						// Parse the input values
						int id = Integer.parseInt(idStr);
						double price = Double.parseDouble(priceStr);
						int yearPublished = Integer.parseInt(yearPublishedStr);
						int stock = Integer.parseInt(stockStr);

						// Create a new product
						Movei music = new Movei(id, name, price, category, stock, yearPublished, "Artist");
						initialProducts.add(music);

						// Add the product to the inventory and update the displayed list
						inventory.addItem(music);
						inventoryListModel.addElement(music.getInfo());
					} catch (NumberFormatException ex) {
						JOptionPane.showMessageDialog(frame, "Invalid input for ID, price, yearPublished, or stock.",
								"Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});

		removeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String selectedItemm = inventoryList.getSelectedValue();
				if (selectedItemm != null) {
					int productId = Integer.parseInt(selectedItemm.split(",")[0].substring(4).trim());
					StockableProduct product = inventory.getItem(productId);
					if (product != null && product instanceof Movei) {
						// currentInvoice.removeProduct(product);
						currentInvoice.addProduct(product);
						// Remove from the displayed list
						inventoryListModel.removeElement(selectedItemm);
						updateInvoiceTextAreaa();
					}
				}
			}
		});

	}

	public void updateInvoiceTextAreaaa() {
		invoiceTextArea.setText(currentInvoice.getInvoice());
	}

	private void initializeMusicButtonsss() {
		addButton = new JButton("Add");
		removeButton = new JButton("Remove");

		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Create a panel to hold the input fields
				JPanel inputPanel = new JPanel(new GridLayout(6, 2));
				JTextField idField = new JTextField(5);
				JTextField nameField = new JTextField(20);
				JTextField priceField = new JTextField(10);
				JTextField categoryField = new JTextField(10);
				JTextField yearPublishedField = new JTextField(5);
				JTextField stockField = new JTextField(5);

				// Add labels and fields to the input panel
				inputPanel.add(new JLabel("ID:"));
				inputPanel.add(idField);
				inputPanel.add(new JLabel("Name:"));
				inputPanel.add(nameField);
				inputPanel.add(new JLabel("Price:"));
				inputPanel.add(priceField);
				inputPanel.add(new JLabel("Category:"));
				inputPanel.add(categoryField);
				inputPanel.add(new JLabel("yearPublished:"));
				inputPanel.add(yearPublishedField);
				inputPanel.add(new JLabel("Stock:"));
				inputPanel.add(stockField);

				// Show the input dialog with the input panel
				int result = JOptionPane.showConfirmDialog(frame, inputPanel, "Enter Product Details",
						JOptionPane.OK_CANCEL_OPTION);

				if (result == JOptionPane.OK_OPTION) {
					// Get values from input fields
					String idStr = idField.getText();
					String name = nameField.getText();
					String priceStr = priceField.getText();
					String category = categoryField.getText();
					String yearPublishedStr = yearPublishedField.getText();
					String stockStr = stockField.getText();

					// Check if any input is missing
					if (idStr.isEmpty() || name.isEmpty() || priceStr.isEmpty() || category.isEmpty()
							|| yearPublishedStr.isEmpty() || stockStr.isEmpty()) {
						JOptionPane.showMessageDialog(frame, "Please fill in all fields.", "Error",
								JOptionPane.ERROR_MESSAGE);
						return;
					}

					try {
						// Parse the input values
						int id = Integer.parseInt(idStr);
						double price = Double.parseDouble(priceStr);
						int yearPublished = Integer.parseInt(yearPublishedStr);
						int stock = Integer.parseInt(stockStr);

						// Create a new product
						Game musiccc = new Game(id, name, price, category, stock, yearPublished, "Artist");
						initialProducts.add(musiccc);

						// Add the product to the inventory and update the displayed list
						inventory.addItem(musiccc);
						inventoryListModel.addElement(musiccc.getInfo());
					} catch (NumberFormatException ex) {
						JOptionPane.showMessageDialog(frame, "Invalid input for ID, price, yearPublished, or stock.",
								"Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});

		// Remove Button for all product types
		removeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String selectedItem = inventoryList.getSelectedValue();
				if (selectedItem != null) {
					int productId = Integer.parseInt(selectedItem.split(",")[0].substring(4).trim());
					StockableProduct product = inventory.getItem(productId);
					if (product != null) {
						currentInvoice.removeProduct(product);
						// Remove from the displayed list
						inventoryListModel.removeElement(selectedItem);
						updateInvoiceTextArea();
					}
				}
			}
		});

	}

	private void loadInitialProducts(String fileName) throws FileNotFoundException, IOException {
		try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
			String line;
			while ((line = reader.readLine()) != null) {
				fileLines.add(line);
				String[] productInfo = line.split(",");
				if (productInfo.length == 7) {
					int id = Integer.parseInt(productInfo[0]);
					String name = productInfo[1];
					double price = Double.parseDouble(productInfo[2]);
					String category = productInfo[3];
					int stock = Integer.parseInt(productInfo[4]);
					int yearPublished = Integer.parseInt(productInfo[5]);
					String additionalInfo = productInfo[6];

					StockableProduct product = null; // Initialize product to null

					switch (category) {
					case "Music":
						product = new Music(id, name, price, category, stock, yearPublished, additionalInfo);
						break;
					case "Movie":
						product = new Movei(id, name, price, category, stock, yearPublished, additionalInfo);
						break;
					case "Game":
						product = new Game(id, name, price, category, stock, yearPublished, additionalInfo);
						break;
					default:
						break;
					}

					if (product != null) {
						inventory.addItem(product);
						initialProducts.add(product);
					}
				}
			}
		}
	}

	private void saveDataToFile(String fileName) {
		try (PrintWriter writer = new PrintWriter(new FileWriter(fileName, false))) {
			for (StockableProduct product : initialProducts) {
				writer.println(product.toFileString());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void removeItemAndUpdateFile(int productId) {
		Iterator<String> iterator = fileLines.iterator();
		while (iterator.hasNext()) {
			String line = iterator.next();
			if (line.startsWith("ID: " + productId)) {
				iterator.remove();
			}
		}

		saveDataToFile(dataFilePath);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			try {
				new InventoryManagementGUI();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		});
	}
}

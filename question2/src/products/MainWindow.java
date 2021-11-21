package products;

import java.lang.*;
import java.util.*;
import java.io.*;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.EventQueue;
import java.awt.event.*;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class MainWindow extends JFrame
{
    private JLabel idLbl;
    private JLabel nameLbl;
    private JLabel priceLbl;
    private JLabel detailLbl;

    private JTextField idTF;
    private JTextField nameTF;
    private JTextField priceTF;
    private JTextArea detailTA;

    private JButton submitBtn;
    private JButton searchBtn;

    private ArrayList<Product> list;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MainWindow frame = new MainWindow();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public MainWindow() {

        GridBagLayout grid = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        this.setLayout(grid);
        setTitle("Products Form");
        GridBagLayout layout = new GridBagLayout();
        setLayout(layout);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        idLbl = new JLabel("UPC Code:");
        this.add(idLbl, gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        idTF = new JTextField("0000");
        this.add(idTF, gbc);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipady = 20;
        gbc.gridx = 0;
        gbc.gridy = 1;
        nameLbl = new JLabel("Product Name:");
        this.add(nameLbl, gbc);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 1;
        gbc.gridy = 1;
        nameTF = new JTextField("Unknown");
        this.add(nameTF, gbc);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 2;
        priceLbl = new JLabel("Price");
        this.add(priceLbl, gbc);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 1;
        gbc.gridy = 2;

        priceTF = new JTextField("0");
        this.add(priceTF, gbc);
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.gridheight = 2;
        detailTA = new JTextArea(100,200);
        this.add(detailTA, gbc);
        detailTA.setVisible(false);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 3;
        submitBtn = new JButton("ADD PRODUCT");
        submitBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                submitAction(e);
            }
        });
        this.add(submitBtn, gbc);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 1;
        gbc.gridy = 3;
        searchBtn = new JButton("SEARCH");
        searchBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                searchAction(e);
            }
        });
        this.add(searchBtn, gbc);
        setSize(400, 400);
        setPreferredSize(getSize());
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        list = new ArrayList<Product>();
        readFile("products.txt");
        updateDetail();
    }

    private void submitAction(ActionEvent evt)
    {
        int pid=Integer.parseInt(idTF.getText());
        String name = nameTF.getText();
        int price=Integer.parseInt(priceTF.getText());
        if(search(pid)==null)
        {
            list.add(new Product(pid,name,price));
            writeFile("products.txt");
            JOptionPane.showMessageDialog(this, "Product Added Successfully!");
            updateDetail();
        }
        else
        {
            JOptionPane.showMessageDialog(this, "Product Already Exists!");
        }
    }

    private void searchAction(ActionEvent evt)
    {
        int pid = 0;
        String msg;

        pid = Integer.parseInt(idTF.getText());
        Product prod = search(pid);

        if(prod!=null)
            msg = prod.toString();
        else
            msg = "Product Not Found!";

        JOptionPane.showMessageDialog(this,msg);
    }

    public void readFile(String filename) {
        try
        {
            String line = "";
            Scanner fileScan = new Scanner(new File(filename));
            while(fileScan.hasNextLine())
            {
                line = fileScan.nextLine();
                String[] token = line.split(",");
                list.add(new Product(Integer.parseInt(token[0]),token[1],
                        Integer.parseInt(token[2])));
            }
            fileScan.close();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    private void updateDetail()
    {
        for(Product p: list)
        {
            detailTA.append(p.toString());
        }
    }

    public Product search(int pid){
        Product prod = null;
        for(Product p: list)
        {
            if(p.getID()==pid)
                prod=p;
        }
        return prod;
    }

    public void writeFile(String filename) {
        try
        {
            String line = "";
            FileWriter writer = new FileWriter(filename);
            for(Product p: list)
            {
                line = p.getID()+","+p.getName()+","+p.getPrice()+"\n";
                writer.write(line);
            }
            writer.close();
        } catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
}


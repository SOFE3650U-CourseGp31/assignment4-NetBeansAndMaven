package products;

public final class Product
{
    private int pid,price;
    private String name;

    public Product(int id,String name,int price)
    {
        this.pid   = id;
        this.name  = name;
        this.price = price;
    }

    public int getID()
    {
        return (this.pid);
    }

    public String getName()
    {
        return (this.name);
    }

    public int getPrice()
    {
        return (this.price);
    }

    public String toString()
    {
        String str = "";
        str += "PRODUCT NAME: "+name+"\n";
        str += "UPC CODE: "+pid+"\n";
        str += "PRICE: "+" $"+price+"\n";
        return str;
    }
}


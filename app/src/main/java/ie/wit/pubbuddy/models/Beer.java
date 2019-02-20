package ie.wit.pubbuddy.models;

import java.io.Serializable;
import java.util.UUID;

public class Beer implements Serializable
{
    public String beerId;
    public String beerName;
    public String bar;
    public double rating;
    public double price;
    public boolean favourite;

    public Beer() {}

    public Beer(String name, String bar, double rating, double price, boolean fav)
    {
        this.beerId = UUID.randomUUID().toString();
        this.beerName = name;
        this.bar = bar;
        this.rating = rating;
        this.price = price;
        this.favourite = fav;
    }

    @Override
    public String toString() {
        return beerId + " " + beerName + ", " + bar + ", " + rating
                + ", " + price + ", fav =" + favourite;
    }
}

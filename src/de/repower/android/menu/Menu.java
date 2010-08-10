package de.repower.android.menu;

import java.util.Date;

public class Menu {
    private final String _category;
    private final String _description;
    private final double _price;
    private final Date _date;

    public Menu(String category, String description, double price, Date date) {
        _category = category;
        _description = description;
        _price = price;
        _date = date;
    }

    public String getCategory() {
        return _category;
    }

    public String getDescription() {
        return _description;
    }

    public double getPrice() {
        return _price;
    }

    public Date getDate() {
        return _date;
    }

    @Override
    public String toString() {
        return "Menu [_category=" + _category + ", _date=" + _date + ", _description=" + _description + ", _price="
                + _price + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((_category == null) ? 0 : _category.hashCode());
        result = prime * result + ((_date == null) ? 0 : _date.hashCode());
        result = prime * result + ((_description == null) ? 0 : _description.hashCode());
        long temp;
        temp = Double.doubleToLongBits(_price);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Menu other = (Menu) obj;
        if (_category == null) {
            if (other._category != null)
                return false;
        } else if (!_category.equals(other._category))
            return false;
        if (_date == null) {
            if (other._date != null)
                return false;
        } else if (!_date.equals(other._date))
            return false;
        if (_description == null) {
            if (other._description != null)
                return false;
        } else if (!_description.equals(other._description))
            return false;
        if (Double.doubleToLongBits(_price) != Double.doubleToLongBits(other._price))
            return false;
        return true;
    }
}

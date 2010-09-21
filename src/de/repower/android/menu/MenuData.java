 // Copyright 2010 Stefan Meier
 //
 // This module is multi-licensed and may be used under the terms
 // of any of the following licenses:
 //
 //  EPL, Eclipse Public License, http://www.eclipse.org/legal
 //  LGPL, GNU Lesser General Public License, http://www.gnu.org/licenses/lgpl.html
 //  AL, Apache License, http://www.apache.org/licenses
 //  BSD, BSD License, http://www.opensource.org/licenses/bsd-license.php
 //
 // Please contact the author if you need another license.
 // This module is provided "as is", without warranties of any kind.
package de.repower.android.menu;

import java.io.Serializable;
import java.util.Date;

public class MenuData  implements Serializable {
    private String _category;
    private String _description;
    private double _price;
    private Date _date;

    protected MenuData(){
        // Serializable requires this constructor
    }
    
    public MenuData(String category, String description, double price, Date date) {
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
        MenuData other = (MenuData) obj;
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

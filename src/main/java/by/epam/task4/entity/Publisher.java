package by.epam.task4.entity;

import java.io.Serializable;
import by.epam.task4.validation.ValidationException;

public class Publisher implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;
    private String country;

    public Publisher() {}

    public Publisher(String name, String country) throws ValidationException {
        if (name.isBlank() || country.isBlank()) {
            throw new ValidationException("invalid name or country");
        }
        this.name = name;
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws ValidationException {
        if (name.isBlank()) {
            throw new ValidationException("invalid name");
        }
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) throws ValidationException {
        if (country.isBlank()) {
            throw new ValidationException("invalid country");
        }
        this.country = country;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((country == null) ? 0 : country.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
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
        Publisher other = (Publisher) obj;
        if (country == null) {
            if (other.country != null)
                return false;
        } else if (!country.equals(other.country))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(this.getClass().getName());
        builder.append(" [name=");
        builder.append(name);
        builder.append(", country=");
        builder.append(country);
        builder.append("]");
        return builder.toString();
    }

}

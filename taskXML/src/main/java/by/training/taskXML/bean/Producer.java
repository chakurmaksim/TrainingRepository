package by.training.taskXML.bean;

public class Producer {
    /**
     * Producer name.
     */
    private String name;
    /**
     * Producer address.
     */
    private Address address;

    /**
     * Instantiating address variable.
     */
    public Producer() {
        address = new Address();
    }

    public class Address {
        /**
         * Country name.
         */
        private String country;
        /**
         * Postcode.
         */
        private int postcode;
        /**
         * Region name.
         */
        private String region;
        /**
         * District name.
         */
        private String district;
        /**
         * City or village name.
         */
        private String locality;
        /**
         * Name of the street.
         */
        private String street;
        /**
         * Building number.
         */
        private int building;
        /**
         * Designation of the corps.
         */
        private char corps = 0;

        /**
         * Get method.
         *
         * @return name of the country
         */
        public String getCountry() {
            return country;
        }

        /**
         * Set method.
         *
         * @param newCountry country name
         */
        public void setCountry(final String newCountry) {
            country = newCountry;
        }

        /**
         * Get method.
         *
         * @return number of the postcode
         */
        public int getPostcode() {
            return postcode;
        }

        /**
         * Set method.
         *
         * @param newPostcode postcode
         */
        public void setPostcode(final int newPostcode) {
            postcode = newPostcode;
        }

        /**
         * Get method.
         *
         * @return name of the region
         */
        public String getRegion() {
            return region;
        }

        /**
         * Set method.
         *
         * @param newRegion the region name
         */
        public void setRegion(final String newRegion) {
            region = newRegion;
        }

        /**
         * Get method.
         *
         * @return the district name
         */
        public String getDistrict() {
            return district;
        }

        /**
         * Set method.
         *
         * @param newDistrict the district name
         */
        public void setDistrict(final String newDistrict) {
            district = newDistrict;
        }

        /**
         * Get method.
         *
         * @return name of the city or village
         */
        public String getLocality() {
            return locality;
        }

        /**
         * Set method.
         *
         * @param newLocality city or village name
         */
        public void setLocality(final String newLocality) {
            locality = newLocality;
        }

        /**
         * Get method.
         *
         * @return street name
         */
        public String getStreet() {
            return street;
        }

        /**
         * Set method.
         *
         * @param newStreet street name
         */
        public void setStreet(final String newStreet) {
            street = newStreet;
        }

        /**
         * Get method.
         *
         * @return number of the building
         */
        public int getBuilding() {
            return building;
        }

        /**
         * Set method.
         *
         * @param newBuilding building number
         */
        public void setBuilding(final int newBuilding) {
            building = newBuilding;
        }

        /**
         * Get method.
         *
         * @return designation of the building corps
         */
        public char getCorps() {
            return corps;
        }

        /**
         * Set method.
         *
         * @param newCorps corps designation
         */
        public void setCorps(final char newCorps) {
            corps = newCorps;
        }

        /**
         * Overridden toString() method.
         *
         * @return sting representation of the producer address
         */
        @Override
        public String toString() {
            if (region == null && district == null && corps == 0) {
                return "Адрес{"
                        + "страна='" + country + '\''
                        + ", индекс=" + postcode
                        + ", населенный пункт='" + locality + '\''
                        + ", улица='" + street + '\''
                        + ", дом=" + building
                        + '}';
            } else if (district == null && corps == 0) {
                return "Адрес{"
                        + "страна='" + country + '\''
                        + ", индекс=" + postcode
                        + ", область='" + region + '\''
                        + ", район='" + locality + '\''
                        + ", улица='" + street + '\''
                        + ", дом=" + building
                        + '}';
            } else if (district == null) {
                return "Адрес{"
                        + "страна='" + country + '\''
                        + ", индекс=" + postcode
                        + ", область='" + region + '\''
                        + ", район='" + locality + '\''
                        + ", улица='" + street + '\''
                        + ", дом=" + building
                        + ", корп.=" + corps
                        + '}';
            } else if (corps == 0) {
                return "Адрес{"
                        + "страна='" + country + '\''
                        + ", индекс=" + postcode
                        + ", область='" + region + '\''
                        + ", район='" + district + '\''
                        + ", населенный пункт='" + locality + '\''
                        + ", улица='" + street + '\''
                        + ", дом=" + building
                        + '}';
            }
            return "Адрес{"
                    + "страна='" + country + '\''
                    + ", индекс=" + postcode
                    + ", область='" + region + '\''
                    + ", район='" + district + '\''
                    + ", населенный пункт='" + locality + '\''
                    + ", улица='" + street + '\''
                    + ", дом=" + building
                    + ", корп.=" + corps
                    + '}';
        }
    }

    /**
     * Get method.
     *
     * @return producer name
     */
    public String getName() {
        return name;
    }

    /**
     * Set method.
     *
     * @param newName producer name
     */
    public void setName(final String newName) {
        name = newName;
    }

    /**
     * Set method.
     *
     * @param newAddress producer address
     */
    public void setAddress(final Address newAddress) {
        address = newAddress;
    }

    /**
     * Get method.
     *
     * @return producer address
     */
    public Address getAddress() {
        return address;
    }

    /**
     * Overridden toString() method.
     * @return producer name and its address
     */
    @Override
    public String toString() {
        return "Изготовитель{"
                + "наименование='" + name + '\''
                + ", место нахождения=" + address
                + '}';
    }
}

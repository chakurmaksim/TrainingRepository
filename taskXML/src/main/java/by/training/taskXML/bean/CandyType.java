package by.training.taskXML.bean;

public abstract class CandyType {
    /**
     * Boolean variable that shows is candy wrapped.
     */
    private boolean isWrapped = false;
    /**
     * Boolean variable that shows is candy glazed.
     */
    private boolean isGlazed = false;

    /**
     * Getter.
     *
     * @return boolean variable that shows is candy wrapped
     */
    public boolean isWrapped() {
        return isWrapped;
    }

    /**
     * Setter.
     *
     * @param newWrapped boolean variable that shows is candy wrapped
     */
    public void setWrapped(final boolean newWrapped) {
        isWrapped = newWrapped;
    }

    /**
     * Getter.
     *
     * @return boolean variable that shows is candy glazed
     */
    public boolean isGlazed() {
        return isGlazed;
    }

    /**
     * Setter.
     *
     * @param newGlazed boolean variable that shows is candy glazed
     */
    public void setGlazed(final boolean newGlazed) {
        isGlazed = newGlazed;
    }

    public static class Iris extends CandyType {
        /**
         * Production method of the candy.
         */
        private String productionMethod;
        /**
         * Structure of the Iris.
         */
        private String structure;

        /**
         * Getter.
         *
         * @return production method
         */
        public String getProductionMethod() {
            return productionMethod;
        }

        /**
         * Setter.
         *
         * @param newProductionMethod type of the production method
         */
        public void setProductionMethod(final String newProductionMethod) {
            productionMethod = newProductionMethod;
        }

        /**
         * Getter.
         *
         * @return iris structure
         */
        public String getStructure() {
            return structure;
        }

        /**
         * Setter.
         *
         * @param newStructure of Iris candy
         */
        public void setStructure(final String newStructure) {
            structure = newStructure;
        }

        /**
         * Overridden toString() method.
         *
         * @return basic properties of Iris
         */
        @Override
        public String toString() {
            return "Iris{"
                    + "isWrapped=" + isWrapped()
                    + ", isGlazed=" + isGlazed()
                    + ", productionMethod='" + productionMethod + '\''
                    + ", structure='" + structure + '\''
                    + '}';
        }
    }

    public static class Caramel extends CandyType {
        /**
         * Sort or grade of the candy.
         */
        private String grade;

        /**
         * Processing method of the candy.
         */
        private String processingMethod;

        /**
         * Getter.
         *
         * @return grade
         */
        public String getGrade() {
            return grade;
        }

        /**
         * Setter.
         *
         * @param newGrade grade
         */
        public void setGrade(final String newGrade) {
            grade = newGrade;
        }

        /**
         * Getter.
         *
         * @return processing method
         */
        public String getProcessingMethod() {
            return processingMethod;
        }

        /**
         * Setter.
         *
         * @param newProcessingMethod processing method
         */
        public void setProcessingMethod(final String newProcessingMethod) {
            processingMethod = newProcessingMethod;
        }

        /**
         * Overridden toString() method.
         *
         * @return basic properties of Caramel
         */
        @Override
        public String toString() {
            return "Caramel{"
                    + "isWrapped=" + isWrapped()
                    + ", isGlazed=" + isGlazed()
                    + ", grade='" + grade + '\''
                    + ", processingMethod='" + processingMethod + '\''
                    + '}';
        }
    }

    public static class Chocolate extends CandyType {
        /**
         * Type of glaze.
         */
        private String glaze;
        /**
         * Type of body.
         */
        private String body;

        /**
         * Getter.
         *
         * @return glaze
         */
        public String getGlaze() {
            return glaze;
        }

        /**
         * Setter.
         *
         * @param newGlaze type of glaze
         */
        public void setGlaze(final String newGlaze) {
            glaze = newGlaze;
        }

        /**
         * Getter.
         *
         * @return body
         */
        public String getBody() {
            return body;
        }

        /**
         * Setter.
         *
         * @param newBody type of body
         */
        public void setBody(final String newBody) {
            body = newBody;
        }

        /**
         * Overridden toString() method.
         *
         * @return basic properties of Chocolate
         */
        @Override
        public String toString() {
            return "Chocolate{"
                    + "isWrapped=" + isWrapped()
                    + ", isGlazed=" + isGlazed()
                    + ", glaze='" + glaze + '\''
                    + ", body='" + body + '\''
                    + '}';
        }
    }
}

package by.training.taskComposite.bean;

public class Symbol implements TextComponent {
    /**
     * Symbol.
     */
    private Character symbol;

    /**
     * Constructor.
     *
     * @param newSymbol symbol
     */
    public Symbol(final char newSymbol) {
        this.symbol = newSymbol;
    }

    /**
     * Method to restore a word from symbols.
     *
     * @return string of the symbol
     */
    @Override
    public String concatenate() {
        return symbol.toString();
    }
}

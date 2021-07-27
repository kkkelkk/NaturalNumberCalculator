import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumber2;

/**
 * Controller class.
 *
 * @author Baicen Wu
 */
public final class NNCalcController1 implements NNCalcController {

    /**
     * Model object.
     */
    private final NNCalcModel model;

    /**
     * View object.
     */
    private final NNCalcView view;

    /**
     * Useful constants.
     */
    private static final NaturalNumber TWO = new NaturalNumber2(2),
            INT_LIMIT = new NaturalNumber2(Integer.MAX_VALUE);

    /**
     * Updates this.view to display this.model, and to allow only operations
     * that are legal given this.model.
     *
     * @param model
     *            the model
     * @param view
     *            the view
     * @ensures [view has been updated to be consistent with model]
     */
    private static void updateViewToMatchModel(NNCalcModel model,
            NNCalcView view) {
        final NaturalNumber ZERO = new NaturalNumber2(0);

        NaturalNumber top = model.top();
        NaturalNumber bottom = model.bottom();
        /*
         * according to subtract contract, bottom has to be less than top
         */

        if (bottom.compareTo(top) > 0) {
            view.updateSubtractAllowed(false);
        } else {
            view.updateSubtractAllowed(true);
        }
        /*
         * according to divide contract, bottom cannot be 0
         */
        if (bottom.equals(ZERO)) {
            view.updateDivideAllowed(false);
        } else {
            view.updateDivideAllowed(true);
        }
        /*
         * according to power and root contract, bottom has to be less than
         * maximum of integer
         */
        if (bottom.compareTo(INT_LIMIT) > 0) {
            view.updatePowerAllowed(false);
            view.updateRootAllowed(false);
        } else {
            view.updatePowerAllowed(true);
            view.updateRootAllowed(true);
        }
        /*
         * according to root contract, bottom has to be greater than 2
         */
        if (bottom.compareTo(TWO) < 0) {
            view.updateRootAllowed(false);
        } else {
            view.updateRootAllowed(true);
        }

        view.updateTopDisplay(top);
        view.updateBottomDisplay(bottom);

    }

    /**
     * Constructor.
     *
     * @param model
     *            model to connect to
     * @param view
     *            view to connect to
     */
    public NNCalcController1(NNCalcModel model, NNCalcView view) {
        this.model = model;
        this.view = view;
        updateViewToMatchModel(model, view);
    }

    @Override
    public void processClearEvent() {
        /*
         * Get alias to bottom from model
         */
        NaturalNumber bottom = this.model.bottom();
        /*
         * Update model in response to this event
         */
        bottom.clear();
        /*
         * Update view to reflect changes in model
         */
        updateViewToMatchModel(this.model, this.view);
    }

    @Override
    public void processSwapEvent() {
        /*
         * Get aliases to top and bottom from model
         */
        NaturalNumber top = this.model.top();
        NaturalNumber bottom = this.model.bottom();
        /*
         * Update model in response to this event
         */
        NaturalNumber temp = top.newInstance();
        temp.transferFrom(top);
        top.transferFrom(bottom);
        bottom.transferFrom(temp);
        /*
         * Update view to reflect changes in model
         */
        updateViewToMatchModel(this.model, this.view);
    }

    @Override
    public void processEnterEvent() {
        /*
         * Update view to reflect changes in model
         */
        NaturalNumber top = this.model.top();
        NaturalNumber bottom = this.model.bottom();
        /*
         * enter bottom operand to top
         */
        top.copyFrom(bottom);
        /*
         * Update view to reflect changes in model
         */
        updateViewToMatchModel(this.model, this.view);

    }

    @Override
    public void processAddEvent() {
        /*
         * Update view to reflect changes in model
         */
        NaturalNumber top = this.model.top();
        NaturalNumber bottom = this.model.bottom();
        /*
         * update bottom by adding top and clears top
         */
        bottom.add(top);
        top.clear();
        /*
         * Update view to reflect changes in model
         */
        updateViewToMatchModel(this.model, this.view);
    }

    @Override
    public void processSubtractEvent() {
        /*
         * Update view to reflect changes in model
         */
        NaturalNumber top = this.model.top();
        NaturalNumber bottom = this.model.bottom();
        /*
         * update bottom subtracted by top and clears top
         */
        top.subtract(bottom);
        bottom.transferFrom(top);
        /*
         * Update view to reflect changes in model
         */
        updateViewToMatchModel(this.model, this.view);

    }

    @Override
    public void processMultiplyEvent() {
        /*
         * Update view to reflect changes in model
         */
        NaturalNumber top = this.model.top();
        NaturalNumber bottom = this.model.bottom();
        /*
         * Update bottom multiplying by top and clears top
         */
        bottom.multiply(top);
        top.clear();
        /*
         * Update view to reflect changes in model
         */
        updateViewToMatchModel(this.model, this.view);
    }

    @Override
    public void processDivideEvent() {
        /*
         * Update view to reflect changes in model
         */
        NaturalNumber top = this.model.top();
        NaturalNumber bottom = this.model.bottom();
        /*
         * Update bottom divided by top and update top to remainder of division
         */
        NaturalNumber remainder = top.divide(bottom);
        bottom.transferFrom(top);
        top.transferFrom(remainder);
        /*
         * Update view to reflect changes in model
         */
        updateViewToMatchModel(this.model, this.view);

    }

    @Override
    public void processPowerEvent() {

        /*
         * Update view to reflect changes in model
         */
        NaturalNumber top = this.model.top();
        NaturalNumber bottom = this.model.bottom();
        /*
         * Update bottom to #top^#bottom and clears top
         */

        top.power(bottom.toInt());
        bottom.transferFrom(top);
        /*
         * Update view to reflect changes in model
         */
        updateViewToMatchModel(this.model, this.view);

    }

    @Override
    public void processRootEvent() {
        /*
         * Update view to reflect changes in model
         */
        NaturalNumber top = this.model.top();
        NaturalNumber bottom = this.model.bottom();
        /*
         * Update bottom and clears top
         */
        top.root(bottom.toInt());
        bottom.transferFrom(top);
        ;
        /*
         * Update view to reflect changes in model
         */
        updateViewToMatchModel(this.model, this.view);

    }

    @Override
    public void processAddNewDigitEvent(int digit) {
        /*
         * Update view to reflect changes in model
         */
        NaturalNumber bottom = this.model.bottom();

        /*
         * Update bottom adding a new digit
         */
        bottom.multiplyBy10(digit);

        /*
         * Update view to reflect changes in model
         */
        updateViewToMatchModel(this.model, this.view);

    }

}

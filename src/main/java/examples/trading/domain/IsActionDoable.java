package examples.trading.domain;

import domain.Action;
import domain.State;
import domain.qvalues.statefree.features.Feature;

public class IsActionDoable implements Feature {

    private boolean isBuyable(double money, double price, int volume) {
        return money - (price * volume) >= 0.0;
    }

    private boolean isSellable(int commodity, int volume) {
        return commodity - volume >= 0.0;
    }

    @Override
    public double calculateFeature(State state, Action action) {
        TradingState tradingState = (TradingState) state;
        TradingAction tradingAction = (TradingAction) action;
        if(tradingAction.getIndex() == 0) {
            return 1;
        }
        if(tradingAction.getIndex() == 1) {
            return isBuyable(tradingState.getMoney(), tradingState.getLastPrice(), 1) ? 1 : -1;
        }
        if(tradingAction.getIndex() == 2) {
            return isSellable(tradingState.getCommodity(), 1) ? 1 : -1;
        }
        throw new IllegalStateException("Don't mess with domain!");
    }
}

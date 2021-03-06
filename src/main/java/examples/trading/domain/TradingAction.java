package examples.trading.domain;

import domain.Action;
import domain.State;

public class TradingAction extends Action {

    public enum Type {
        BUY,
        SELL,
        NOTHING
    }

    private final int volume;
    private final Type type;

    public TradingAction(int index, int volume, Type type) {
        super(index);
        this.volume = volume;
        this.type = type;
    }

    public int getVolume() {
        return volume;
    }

    public Type getType() {
        return type;
    }

    @Override
    public State apply(State state) {
        TradingState tradingState = (TradingState) state;
        if(type == Type.NOTHING) {
            return tradingState.copy();
        }
        double money = tradingState.getMoney();
        int commodity = tradingState.getCommodity();
        double lastPrice = tradingState.getLastPrice();
        TradingState newState = (TradingState) tradingState.copy();
        if(type == Type.BUY) {
            tradingState.setMoney(money - lastPrice * volume);
            tradingState.setCommodity(commodity + volume);
        } else if(type == Type.SELL) {
            tradingState.setMoney(money + lastPrice * volume);
            tradingState.setCommodity(commodity - volume);
        } else {
            throw new IllegalStateException("Don't mess with domain.");
        }
        return newState;
    }

}

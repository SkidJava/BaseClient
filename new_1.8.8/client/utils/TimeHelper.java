package client.utils;

public class TimeHelper
{
    private long lastMS;

    public long getCurrentMS()
    {
        return System.nanoTime() / 1000000L;
    }

    public long getLastMS()
    {
        return this.lastMS;
    }

    public boolean hasReached(double d)
    {
        return (float)(this.getCurrentMS() - this.lastMS) >= d;
    }

    public void reset()
    {
        this.lastMS = this.getCurrentMS();
    }

    public void setLastMS(long currentMS)
    {
        this.lastMS = currentMS;
    }
}


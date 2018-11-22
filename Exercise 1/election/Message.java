public class Message
{
	private int value;
	private int timeToLive;
	private int id;
	
	public Message(int value, int timeToLive, int id)
	{
		this.id = id;
		this.value = value;
		this.timeToLive = timeToLive;
	}
	
	public int getValue()
	{
		return value;
	}
	
	public int getTimeToLive()
	{
		return timeToLive;
	}
	
	public int getId()
	{
		return id;
	}
	
	public void setValue(int value)
	{
		this.value = value;
	}
	
	public void setTimeToLive(int timeToLive)
	{
		this.timeToLive = timeToLive;
	}
	
	public void setId(int id)
	{
		this.id = id;
	}
	
	//timeToLive--
	public void setTitmeToLiveAutoIteration()
	{
		timeToLive--;
	}
}

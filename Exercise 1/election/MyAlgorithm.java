import teachnet.algorithm.BasicAlgorithm;

import java.awt.Color;
import java.util.Random;

public class MyAlgorithm extends BasicAlgorithm
{
	Color color = Color.WHITE;
	int markInterface = -1;
	String caption;
	String captionBase;
	Random rand = null;
	Message message;
	
	
	int father = 0; //it needs to remember who sent a message first
	
	boolean isFather = false;	//the initiator behaves differently than the others from the image we have on the exercise
								//so we need to differenciate it from the others
	boolean hasReceivedAll = false; //the initiator needs to wait until both messages arrive before sending more
	
	public void setup(java.util.Map<String, Object> config)
	{
		int id = (Integer) config.get("node.id");
		rand = getRandom();
		
		Random randomNumber = new Random();
		
		message = new Message(randomNumber.nextInt(10)+1, 0, id);
		
		captionBase = "node" + id + " value " + message.getValue();
		caption = captionBase;
	}
	public void initiate() //only the initiator does that
	{
		isFather = true;
		
		/*int[] message = new int[3];// id, value, timeToLive
		message[0] = idNode;
		message[1] = number;
		message[2] = timeToLive; *///doesn't work, send() breaks it
		//String message = idNode + " " + number + " " + timetoLive;
		this.message.setTimeToLive(1);
		
		for (int i = 0; i < checkInterfaces(); ++i) //sending a message to all the neighbors, it is a ring so only two
		{
			
			send(i, new Message(message.getValue(), message.getTimeToLive(), message.getId()));
		}
		
		//color = Color.RED; //has sent a message (following example L03 slide 18)
	}
	public void receive(int interf, Object message)
	{
		if(!(message instanceof Message))
		{
			System.out.println("error Object is wrong");
			return;
		}
		Message messageTest = (Message) message; //doesn't work otherwise, I don't know why
		
		if(checkInterfaces() != 2)
		{
			System.out.println("error topology is not a ring");
			return;
		}
		
		if(!isFather)
		{
			if(messageTest.getTimeToLive() > 1)
				father = interf;
			messageTest.setTitmeToLiveAutoIteration(); //remove one step to the time to live
			
			if(messageTest.getTimeToLive() == 0)//the node that needs to be compared
			{
				if(!hasReceivedAll)
				{
					if(messageTest.getValue() < this.message.getValue())
					{
						send(interf, this.message);
						System.out.println("end of time to live winner " + this.message.getId());
					}
					else
					{
						send(interf, messageTest);
						System.out.println("end of time to live " + this.message.getId());
					}
					hasReceivedAll = true;
					color = Color.GREEN;
				}
				else
				{
					send(interf, new Message(-1, 0, 0));
				}
			}
			else if(messageTest.getTimeToLive() < 0)
			{
				send(father, messageTest);
			}
			else //node is inactive, message transfered to the next node
			{
				send((interf+1)%2, messageTest);
			}
		}
		else //is the initiator
		{
			if(messageTest.getValue() == -1)
			{
				if(color != Color.GREEN)
				{
					System.out.println("done");
					System.out.println("winner is node " + this.message.getId());
					color = Color.GREEN;
				}
				return;
			}
			if(this.message.getValue() < messageTest.getValue())
			{
				System.out.println("node " + messageTest.getId() + " is the winner");
				messageTest.setTimeToLive(this.message.getTimeToLive());
				this.message = messageTest;
			}
			if(hasReceivedAll)
			{
				this.message.setTimeToLive(this.message.getTimeToLive()+1);
				for (int i = 0; i < checkInterfaces(); ++i) //sending a message to all the neighbors, it is a ring so only two
				{
					send(i, new Message(this.message.getValue(), this.message.getTimeToLive(), this.message.getId()));
				}
				hasReceivedAll = false;
			}
			else
				hasReceivedAll = true;
		}
	}
	
}

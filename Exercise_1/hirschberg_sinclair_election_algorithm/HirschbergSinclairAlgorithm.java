/**
Group 8
1. Alexandre Georges Rémi Martin, 406294
2. Benyamin Shafabakhsh, 406305
3. Yared Dejene Dessalk, 406228
4. Anubhav Guha, 406244
5. Jiaqiao Peng, 406038

 * Implementation of Hirschberg Sinclair Algorithm
 * 
 */

import teachnet.algorithm.BasicAlgorithm;
import java.awt.Color;
import java.util.Random;

public class HirschbergSinclairAlgorithm extends BasicAlgorithm
{
    Color color = Color.WHITE;
    String caption;
    Boolean active = true;// node status
    int id;
    int winner;
    int[] token0 = new int[2];// id + direction(clockwise or anticlockwise)
    int[] token1 = new int[2];// id + direction(clockwise or anticlockwise)
    int[] received = new int[2];

    public void setup(java.util.Map<String, Object> config)
    {
        id = (Integer) config.get("node.id");
        updateView();
    }
    public void initiate()
    {
        winner = id;
        token0[0] = id;
        token0[1] = 0;
        send(0, token0);
        token1[0] = id;
        token1[1] = 1;
        send(1, token1);
    }
    public void receive(int interf, Object message)
    {
        received = (int[]) message;
        System.out.printf("Node %d received direction is %d, from %d\n", id, received[1], interf);

        if (received[0] == id)
        {
            if (received[1] == -1)
            {
                active = false;
                color = Color.BLACK;// this node is loser
            }
            else
            {
                if (received[1] != interf)// meassage reach origin node or not
                {
                    winner = id;
                    color = Color.RED;
                    active = false;
                }
            }
        }
        else
        {
            if (active)
            {
                // If it has a smaller ID, it relays the token as requested
                if (received[0] > id)
                {
                    active = false;
                    color = Color.BLACK;
                    token0[0] = received[0];
                    token0[1] = received[1];
                    send(1 - interf, token0);
                    winner = received[0];
                }
                else
                {
                    // If it has a larger ID, send -1 to inform the origin node it is the loser
                    token0[0] = id;
                    token0[1] = 1 - interf;
                    send(1 - interf, token0);
                    System.out.printf("Node %d send direction info is %d, from %d\n", id, token0[1], 1 - interf);
                    token1[0] = received[0];
                    token1[1] = -1;
                    send(interf, token1);
                    winner = id;
                }
            }
            else// If not active, just pass the message forward
            {
                token1[0] = received[0];
                token1[1] = received[1];
                send(1 - interf, token1);
                if(received[0] > winner)
                    winner = received[0];
            }
        }
        updateView();
    }
    private void updateView(){
        caption = "Node: " + id +" winner: "+ winner;
    }
}

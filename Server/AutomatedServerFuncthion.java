package Server;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import Client.Command;
import Client.CommunicationController;
import Entity.Msg;

public class AutomatedServerFuncthion extends TimerTask {
	public static CommunicationController client;



	@Override
	public void run() 
	{
		Msg msg=new Msg("",Command.automatedRun);
		try {
			client.sendToServer(msg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}	




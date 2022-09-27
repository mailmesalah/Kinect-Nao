package kinecttonaoui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.aldebaran.proxy.ALTextToSpeechProxy;

import edu.ufl.digitalworlds.j4k.J4KSDK;
import edu.ufl.digitalworlds.j4k.Skeleton;

public class KinectToNao extends J4KSDK {

	public static String NAO_IP = "127.0.0.1";
	public static int NAO_PORT = 9559;
	
	public static ViewerPanel3D skeletonPanel;
	public static boolean isConnected=false;
		
	public KinectToNao(){
		skeletonPanel = new ViewerPanel3D();
		skeletonPanel.setPreferredSize(new Dimension(512, 424));
	}
	
	@Override
	public void onColorFrameEvent(byte[] arg0) {	
		
	}

	@Override
	public void onDepthFrameEvent(short[] arg0, byte[] arg1, float[] arg2, float[] arg3) {
		
	}

	@Override
	public void onSkeletonFrameEvent(boolean[] skeleton_tracked, float[] joint_position, float[] joint_orientation, byte[] joint_status) {
		skeletonPanel.skeletons=new Skeleton[getMaxNumberOfSkeletons()]; 
        for(int i=0;i<getMaxNumberOfSkeletons();i++) {
        	skeletonPanel.skeletons[i]=Skeleton.getSkeleton(i, skeleton_tracked, joint_position, joint_orientation, joint_status, this); 		
        }
        
        skeletonPanel.draw();                
	}

	
	public static void main(String[] args)
	{
				
		KinectToNao kinect=new KinectToNao();
		
		JFrame mainWindow = new JFrame();
		mainWindow.setBounds(0, 0, 1000, 700);
	
		JPanel contentPane = new JPanel();
		mainWindow.setContentPane(contentPane);
		
		JPanel settingsPanel = new JPanel();
		JTextField ipAddressText = new JTextField();
		ipAddressText.setText("127.0.0.1");
		ipAddressText.setPreferredSize(new Dimension(100, 30));
		JButton startButton = new JButton("Start");
		settingsPanel.add(ipAddressText);
		settingsPanel.add(startButton);
		contentPane.add(kinect.skeletonPanel, BorderLayout.CENTER);
		contentPane.add(settingsPanel, BorderLayout.SOUTH);
		
		mainWindow.setVisible(true);
		mainWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		mainWindow.addWindowListener(new WindowListener() {
			
			@Override
			public void windowOpened(WindowEvent e) {
				
			}
			
			@Override
			public void windowIconified(WindowEvent e) {
				
			}
			
			@Override
			public void windowDeiconified(WindowEvent e) {
				
			}
			
			@Override
			public void windowDeactivated(WindowEvent e) {
				
			}
			
			@Override
			public void windowClosing(WindowEvent e) {	
				kinect.stop();					
			}
			
			@Override
			public void windowClosed(WindowEvent e) {
				kinect.stop();		
			}
			
			@Override
			public void windowActivated(WindowEvent e) {				
				
			}
		});
		
		startButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!kinect.start(SKELETON)){
					JOptionPane.showMessageDialog(null, "Couldnt start Kinect");					
				}else{
					isConnected=true;					
				}
				
				/*try{
					ALTextToSpeechProxy tts = new ALTextToSpeechProxy(ipAddressText.getText().trim(), NAO_PORT);
					tts.say("Hello, world");
				}catch(Exception ex){
					JOptionPane.showMessageDialog(null, "Nao Error: "+ex.getMessage());
				}*/
				
			}
		});
						
	}



}

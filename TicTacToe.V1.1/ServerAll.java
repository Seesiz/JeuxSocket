package tictac;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
public class ServerAll extends javax.swing.JFrame implements Runnable{
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    /**
     * Creates new form ServerAll
     */
    public ServerAll() {
        initComponents();
    }
    	int nombreClients;   
    private List<Socket> ClientsConnectes=new ArrayList<>();

	public void run() {
		try {
   
			ServerSocket ss=new ServerSocket(234);
			while(true){
				System.out.println("J'attend toujours la connexion de clients");
				Socket s=ss.accept();
				ClientsConnectes.add(s);
				++nombreClients;
				new Conversation(s,nombreClients).start();
			}
                   
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	public void BroadCast(String message){
		//Parcourir toutes les sockets;
		try {
			for(Socket s:ClientsConnectes){
				PrintWriter pw=new PrintWriter(s.getOutputStream(),true);
				pw.println(message);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	class Conversation extends Thread{
		private Socket socket;
		private int NumeroClient;
			
		public Conversation(Socket socket,int NumeroClient) {
			super();
			this.socket = socket;
			this.NumeroClient=NumeroClient;
		}

		@Override
		public void run() {
		//Code de la conversation
                try {
				//lire la donnée reçu
				InputStream is=socket.getInputStream();
				InputStreamReader isr= new InputStreamReader(is);
				BufferedReader br=new BufferedReader(isr);
				//envoyer une donnée
				OutputStream os=socket.getOutputStream();
				PrintWriter pw=new PrintWriter(os, true);
				
				String IP=socket.getRemoteSocketAddress().toString();
				System.out.println("Connexion du Client N° "+NumeroClient+" IP:"+IP);
                    pw.println(NumeroClient);
				while(true){
					//req rép
					String req;
					while((req=br.readLine())!=null){
						System.out.println("le client"+NumeroClient+" a envoyé "+req);
				BroadCast(req);
					}	
				}
				
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}
	}       
    @SuppressWarnings("unchecked")
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);

        jButton1.setText("Connexion Serveur");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Déconnexion");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(57, 57, 57)
                .addComponent(jButton1)
                .addGap(33, 33, 33)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(108, 108, 108)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addGap(33, 33, 33)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(105, Short.MAX_VALUE))
        );

        pack();
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        new Thread(new ServerAll()).start();
        jLabel1.setText("Le serveur est en marche !!");
    }

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ServerAll.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ServerAll.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ServerAll.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ServerAll.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ServerAll().setVisible(true);
            }
        });
    }
}

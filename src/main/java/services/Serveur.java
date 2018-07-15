package services;


//import java.util.*;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.swing.*;
import entites.*;
import rmi.IBanqueData;

//import rmi.*;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.awt.*;
import java.awt.event.*;

public class Serveur extends JFrame implements ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextArea zonerecep;
	 private JButton qt;
	 private JPanel pan1,pan2;

	 public Serveur()
	 {
	     zonerecep=new JTextArea(15,40);
	    qt=new JButton("Quitter");
	    setTitle(" Serveur TCP Multiclients");
	    pan1=new JPanel();
	    pan2=new JPanel();
	    pan1.add(new JScrollPane(zonerecep));
	    qt.addActionListener(this);
	    pan2.add(qt);
	    add(pan1,BorderLayout.CENTER);
	    add(pan2,BorderLayout.SOUTH);
	    setSize(500,600);
	    setLocationRelativeTo(null);
	    setVisible(true);

	    try
		{
	ServiceRMI service = new ServiceRMI();
	
	LocateRegistry.createRegistry(1099);
	Naming.rebind("rmi://localhost:1099/BV",service);
	} catch (Exception e)
	{
       System.out.println(e.getMessage());
	
    }

	     
	 }

	 @SuppressWarnings("serial")
	public
	// classe interne ServiceRMI
	    class ServiceRMI extends UnicastRemoteObject implements IBanqueData
	    {
	    	private EntityManager em;
	        public ServiceRMI() throws RemoteException
	        {
	        	EntityManagerFactory emf = Persistence.createEntityManagerFactory("BANQUE");
	    		this.em = emf.createEntityManager();
	        }
	       public void addAgence(Agence ag) throws RemoteException {
				// TODO Auto-generated method stub
	    	   /*creation d'une transaction */
				EntityTransaction transaction = em.getTransaction();
			/* Demarage de la transaction */
				transaction.begin();
				try{
					/* Enregistrer l'agence dans la base de donnees */
						em.persist(ag);
						/* Validation de la transaction si ok */
						transaction.commit();
					}
					catch(Exception e){
						/* Annulation de la transaction si KO */
						transaction.rollback();
						e.printStackTrace();
						
					}
			}
			@SuppressWarnings("unchecked")
			public List<Agence> listAgences() throws RemoteException {
				// TODO Auto-generated method stub
				Query query = em.createQuery("select ag from Agence ag");
				return query.getResultList();
			}
			public void addClient(Client cli) throws RemoteException {
				// TODO Auto-generated method stub
				/*creation d'une transaction */
				EntityTransaction transaction = em.getTransaction();
			/* Demarage de la transaction */
				transaction.begin();
				try{
						/* Enregistrer l'agence dans la base de donnees */
						em.persist(cli);
						/* Validation de la transaction si ok */
						transaction.commit();
					}
					catch(Exception e){
						/* Annulation de la transaction si KO */
						transaction.rollback();
						e.printStackTrace();
						
					}
			}
			@SuppressWarnings("unchecked")
			public List<Client> listClientsParAgence() throws RemoteException {
				// TODO Auto-generated method stub
				Query query = em.createQuery("Select cli, ag from Client cli, Agence ag Where cli.numag = ag.numag GROUP BY ag.numag ");
				return query.getResultList();
			}
			public void addCompte(Compte comp) throws RemoteException {
				// TODO Auto-generated method stub
				/*creation d'une transaction */
				EntityTransaction transaction = em.getTransaction();
				/* Demarage de la transaction */
				transaction.begin();
				try{
					/* Enregistrer le compte dans la base de donnees */
					em.persist(comp);
					/* Validation de la transaction si ok */
					transaction.commit();
				}
				catch(Exception e){
					/* Annulation de la transaction si KO */
					transaction.rollback();
					e.printStackTrace();
					
				}
				
			}
			
			@SuppressWarnings("unchecked")
			public List<Compte> listComptesClient(int num) throws RemoteException {
				// TODO Auto-generated method stub
				Query query = em.createQuery("select cmpt from Compte cmpt where cmpt.numCli like :x");
				query.setParameter("x", num);
				return query.getResultList();			
			}
			
			public void addOperation(Operation op) throws RemoteException {
				// TODO Auto-generated method stub
				/*creation d'une transaction */
				EntityTransaction transaction = em.getTransaction();
				if(op.getSens()=='CR') {
					
				}
				/* Demarage de la transaction */
				transaction.begin();
				try{
					/* Enregistrer l'operation dans la base de donnees */
					em.persist(op);
					/* Validation de la transaction si ok */
					transaction.commit();
				}
				catch(Exception e){
					/* Annulation de la transaction si KO */
					transaction.rollback();
					e.printStackTrace();
					
				}
				
			}
			@SuppressWarnings("unchecked")
			public List<Operation> listReleve() throws RemoteException {
				// TODO Auto-generated method stub
				Query query  = em.createQuery("select op, cmpt.sens,cmpt.solde from Operation op,Compte cmpt where op.numCpt = cmpt.numCpt ");
				return query.getResultList();
			}
	 }//fin classe interne

	  public void actionPerformed(ActionEvent e)
	    {
	        dispose();
	        System.exit(0);
	    }



	    public static void main(String args[])
	    {
	        new Serveur();
	    }
	
}


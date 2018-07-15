package entites;

import java.net.MalformedURLException;
import java.rmi.*;
//import java.rmi.NotBoundException;
//import java.rmi.RemoteException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import rmi.IBanqueData;
import services.Serveur;

public class DemoBanque {

	@SuppressWarnings("unused")
	public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException {
		// TODO Auto-generated method stub
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("BANQUE");
		EntityManager em = emf.createEntityManager();
		Serveur serveur = new Serveur();
		IBanqueData objetServeur = (IBanqueData) Naming.lookup("rmi://localhost:1099/BV");
		
		//serveur.ServiceRMI service = new serveur.ServiceRMI();
		Agence ag = new Agence();
		//ag.setNumag(1);
		ag.setNomag("CBAO");
		ag.setAdresseag("Point E");
		try {
			objetServeur.addAgence(ag);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<Agence> l = objetServeur.listAgences();
		for(Agence a:l)
		{
			System.out.println(a.getNumag()+" "+a.getNomag()+" "+a.getAdresseag());
		}
		 Client cli = new Client();
		cli.setNom("Yero");
		cli.setPrenom("Diallo");
		cli.setNumag(ag);
		objetServeur.addClient(cli);
		
	}

}

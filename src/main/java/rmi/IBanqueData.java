package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import entites.*;

public interface IBanqueData extends Remote {

	public void addAgence(Agence ag) throws RemoteException;
	public List<Agence> listAgences() throws RemoteException;
	public void addClient(Client cli) throws RemoteException;
	public List<Client> listClientsParAgence() throws RemoteException;
	public void addCompte(Compte comp) throws RemoteException;
	public List<Compte> listComptesClient(int num) throws RemoteException; 
	public void addOperation(Operation op) throws RemoteException;
	public List<Operation> listReleve() throws RemoteException;
}

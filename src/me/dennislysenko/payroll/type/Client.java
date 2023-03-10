package me.dennislysenko.payroll.type;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Client {

	private Integer ID;
	private String LABEL;
	private String[] ALIAS;

	private static List<Client> clients = new ArrayList<Client>();
	private static File data = new File("clients.dat");

	public Client(Integer id, String label, String[] alias) {
		ID = id;
		LABEL = label;
		ALIAS = alias;
	}
	
	public Integer getId() {
		return ID;
	}
	
	public String getLabel() {
		return LABEL;
	}	
	
	public String[] getAlias() {
		return ALIAS;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Client) {
			Client client = (Client) obj;
			if (client.getId().equals(getId())) {
				return true;
			}
		}
		return false;
	}

	public static void loadClients() {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(data));
			String line;
			while ((line = reader.readLine()) != null) {
				if (!line.isEmpty()) {
					String[] vars = line.split(":");
					
					Integer id = new Integer(vars[0]);
					String label = vars[1];
					String[] alias = vars[2].replace("[", "").replace("]", "").split(",");
					String[] alias2 = new String[alias.length];

					for (int i = 0; i < alias.length; i++) {
						alias2[i] = alias[i].trim();
					}
					
					clients.add(new Client(id, label, alias2));
				}
			}
			reader.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public static void addClient(Integer id, String label, String[] alias) {
		List<String> alias2 = new ArrayList<String>();
		for (String alias1 : alias) {
			alias2.add(alias1);
		}
		try {
			BufferedReader reader = new BufferedReader(new FileReader(data));
			List<String> lines = new ArrayList<String>();
			String line;
			while ((line = reader.readLine()) != null) {
				if (!line.isEmpty()) {
					lines.add(line);
				}
			}
			reader.close();
			PrintWriter writer = new PrintWriter(data);
			for (String line1 : lines) {
				writer.println(line1);
			}
			writer.println(id.toString() + ":" + label + ":" + alias2);
			writer.close();
			clients.add(new Client(id, label, alias));
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public static Boolean exists(String client) {
		Boolean output = false;
		if (getClient(client) != null) {
			output = true;
		}
		return output;
	}

	public static Client getClient(String client) {
		Client output = null;
		for (Client client1 : clients) {
			List<String> keys = new ArrayList<String>();
			keys.add(client1.getLabel().toUpperCase());
			for (String alias : client1.getAlias()) {
				keys.add(alias.toUpperCase());
			}
			if (keys.contains(client.toUpperCase())) {
				output = client1;
				break;
			}
		}
		return output;
	}
	
	public static Client getClient(Integer id) {
		Client output = null;
		for (Client client : clients) {
			if (client.getId().toString().equalsIgnoreCase(id.toString())) {
				output = client;
			}
		}
		return output;
	}
	
	public static Integer getRowNum() {
		Integer output = 0;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(data));
			while (reader.readLine() != null) {
				output++;
			}
			reader.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return output;
	}

	public static List<Client> getClients() {
		return clients;
	}
	
}

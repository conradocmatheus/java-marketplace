package app.clothing_store.service;

import app.clothing_store.entity.Client;
import app.clothing_store.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    // POST
    // Save a Client
    public String save(Client client){
        this.clientRepository.save(client);
        return "Client: " + client.getName() + ", successfully saved";
    }

    // PUT
    // Update a Client
    public String update(Client client, Long id){
        client.setId(id);
        clientRepository.save(client);
        return (client.getName() + " successfully updated");
    }

    // DELETE
    //  a Client
    public String delete(Long id){
        this.clientRepository.deleteById(id);
        return "Client with id: " + id + " deleted";
    }

    // GET
    // List all Clients
    public List<Client> listAll(){
        return this.clientRepository.findAll();
    }

    // GET
    // Find Client by ID
    public Client findById(Long id){
        return this.clientRepository.findById(id).get();
    }
}

package ru.filit.mdma.dm.repository;

import ru.filit.mdma.dm.entity.client.Client;

import java.util.List;

public interface ClientRepository {

    Client getClient(String clientId);

    List<Client> getClients(Client client);
}

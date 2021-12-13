package ru.filit.mdma.dm.repository;

import ru.filit.mdma.dm.entity.user.Access;

import java.util.List;

public interface AccessRepository {

    List<Access> getAccess(int version, String role);
}

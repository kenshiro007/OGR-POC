package fr.demo.metier.model.utils;

import fr.demo.metier.model.core.AuditObject;
import fr.demo.metier.model.core.IdObject;

import java.util.ArrayList;
import java.util.List;

public abstract class UpdateIdObjectChildrenListWithAudit<T extends IdObject> {

  public List<Long> updateListe(List<T> liste, Long idParent, AuditObject audit) {
    // On supprime les éléments qui n'existent plus
    List<Long> ids = new ArrayList<>(30);
    for (T t : liste) {
      if (t.getId() != null) {
        ids.add(t.getId());
      }
    }
    if (ids.isEmpty()) {
      // Cas où tout est supprimé
      ids.add(0L);
    }
    deleteAllExcept(idParent, ids, audit);
    // Puis on met à jour ou crée les autres
    List<Long> result = new ArrayList<>();
    for (T t : liste) {
      if (t.getId() == null) {
        result.add(create(t, idParent));
      } else {
        update(t, idParent);
        result.add(t.getId());
      }
    }
    return result;
  }

  protected abstract Long create(T t, Long idParent);

  protected abstract void update(T t, Long idParent);

  protected abstract void deleteAllExcept(Long idParent, List<Long> ids, AuditObject audit);

}

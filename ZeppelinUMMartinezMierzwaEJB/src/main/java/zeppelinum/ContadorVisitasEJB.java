package zeppelinum;

import java.util.HashMap;

import javax.annotation.PostConstruct;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.ejb.Startup;

@Singleton(name="Contador")
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
@Startup
public class ContadorVisitasEJB {   
    private HashMap<Integer, Integer> contador;
    
    @PostConstruct
    private void init() {
        contador = new HashMap<Integer, Integer>();
    }
    
    public void nuevaVista(Integer idUsuario) {
        Integer nVisitas = contador.get(idUsuario);
        if(nVisitas == null) {
            contador.put(idUsuario, 1);
        }
        else
            contador.put(idUsuario, nVisitas+1);
    }
    @Lock(LockType.READ)
    public Integer getVisitas(Integer idUsuario) {
        Integer nVisitas = contador.get(idUsuario);
        if(nVisitas == null) {
            return 0;
        }
        return nVisitas;
    }
}
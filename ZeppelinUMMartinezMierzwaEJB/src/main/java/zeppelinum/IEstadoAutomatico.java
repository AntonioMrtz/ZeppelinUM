package zeppelinum;

import javax.ejb.Local;
import javax.ejb.Timer;

@Local
public interface IEstadoAutomatico {    
    public void startUpTimer(String idPedido, int minutos);
    public void cambiarEstado(Timer timer) ;
}
package zeppelinum;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;

@Stateless(name="EstadoAutomatico")
public class EstadoAutomatico implements IEstadoAutomatico {
    // habrá que inyectar el dao de Pedido
    // @EJB(beanName="PedidoDAO")
    // private PedidoDAO pedidoDAO;

    @Resource
    TimerService timerService;

    @Override
    public void startUpTimer(String idPedido, int minutos) {
        timerService.createSingleActionTimer(minutos * 60 * 1000, new TimerConfig(idPedido, true));

    }

    @Override
    @Timeout
    public void cambiarEstado(Timer timer) {
        String idPedido = (String) timer.getInfo();     
        System.out.println("Timer para "+idPedido);     
        //a través del DAO de pedido comprobamos si el pedido está en inicio o ha cambiado
        //Si está en inicio, el pedido pasa a estado cancelado      
        //Si está en otro estado, no se hace nada
    }
}
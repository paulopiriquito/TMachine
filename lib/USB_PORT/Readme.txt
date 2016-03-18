
Para usar o kit USB_PORT num projecto Java do eclipse:

  1 - Copiar os ficheiros usbport.dll e usbport.jar para o directório raíz do projecto eclipse.
      
  2 - Testar o kit executando o usbport.jar
      
      Fazendo: java -jar usbport.jar
	
  2 - No projecto eclipse acrescentar ao "Build Path" o usbport.jar como um Arquivo/Jar externo.
      
      Fazendo:
        Menu Project > Properties > Java Build Path > Libraries > Add External JARs...
        ou
        Menu de contexto do Projecto > Build Path > Add External Archives...

  3 - Para usar o kit (class UsbPort, etc.) fazer o import:
        import isel.leic.usbio.*;
      Para usar as classes utilitárias (class Time, etc.) fazer import:
        import isel.leic.utils.*;


Isel, 2010, Pedro Pereira.
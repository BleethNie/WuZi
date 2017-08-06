package netTest;
import java.net.InetAddress;

public class Searcher {

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        try {
            InetAddress addr = InetAddress.getLocalHost();
            byte[] addrs = addr.getAddress();
            System.out.println(addr.getHostName());
            for (int i = 0; i < addrs.length; i++) {
                System.out.print((addrs[i] & 0xFF) + ".");
            }
           
            if ((addrs[0] & 0x80) == 0)
                System.out.println("the ip class is A");
            else if ((addrs[0] & 0xC0) == 0x80)
                System.out.println("The ip class is B");
            else if ((addrs[0] & 0xE0) == 0xC0){
                System.out.println("The ip class is C");
                addrs[3] = 0;
                for(int i=0;i<256;i++){
                    InetAddress n = InetAddress.getByAddress(addrs);
                    if(n.isReachable(5)){
                        System.out.println(n);
                    }
                    addrs[3]++;
                }
            }
            else if ((addrs[0] & 0xF0) == 0xE0)
                System.out.println("The ip class is D");
            else if ((addrs[0] & 0xF8) == 0xF0)
                System.out.println("The ip class is E");
           
           
        } catch (Exception e) {
            // TODO: handle exception
           
        }
    }
}
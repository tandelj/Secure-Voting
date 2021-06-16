package demo;

import java.io.*;
import java.math.BigInteger;
import java.sql.*;
import java.net.*;
import demo.RSA.*;


public class CLA
{

    public static void main(String[] args) throws Exception
    {
        // CLA is listening to Voter on port 1234
        ServerSocket ss = new ServerSocket(1234);
        // running infinite loop for getting
        // client request
        while (true)
        {
            Socket s = null;

            try
            {
                // socket object to receive incoming client requests
                s = ss.accept();
                System.out.println("A Voter is connected : " + s);

                // obtaining input and out streams
                DataInputStream dis = new DataInputStream(s.getInputStream());
                DataOutputStream dos = new DataOutputStream(s.getOutputStream());

                System.out.println("Assigning new thread for this Voter");

                // create a new thread object
                Thread t = new CLAClientHandler(s, dis, dos);

                // Invoking the start() method
                t.start();

            }
            catch (Exception e){
                s.close();
                e.printStackTrace();
            }
        }
    }
}

// ClientHandler class
class CLAClientHandler extends Thread {

    final DataInputStream dis;
    final DataOutputStream dos;
    final Socket s;



    // Constructor
    public CLAClientHandler(Socket s, DataInputStream dis, DataOutputStream dos) {
        this.s = s;
        this.dis = dis;
        this.dos = dos;
    }
    public String GenerateValNo(){
        return (Integer.toString((int)((Math.random() * 9000000)+1000000)));
    }

    public String connectDB(String FName, String LName, String SSN) throws Exception{
        Connection myConn = null;
        myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/CLA?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC","root","12345678");
        Statement my =myConn.createStatement();
        String query = "select * from VoterList where SSN = ?";
        PreparedStatement pst = myConn.prepareStatement(query);
        pst.setString(1, SSN);
        ResultSet rs = pst.executeQuery();
        if(!rs.next()) {
            System.out.println("New Voter: ");
            String ValNo = GenerateValNo();
            //String query1 = "update VoterList set ValNo = ?,  where SSN = ?";
            String query1 = "insert into VoterList (FName, LName, SSN, ValNo) values (?, ?, ?, ?);";
            PreparedStatement pst1 = myConn.prepareStatement(query1);
            pst1.setString(1, FName);
            pst1.setString(2, LName);
            pst1.setString(3, SSN);
            pst1.setString(4, ValNo);
            pst1.execute();
            return ValNo;
        }
        return "000";
    }
    @Override
    public void run() {
        String rSSN;
        String rFName;
        String rLName;

        BigInteger[] privatekey = new BigInteger[2];

        privatekey[1] = new BigInteger("27389355848173965429007265039983715712801642001937687450424405374311561080085124989885979231216670863137787185638469771487706788583712695425442586943764226614035221958869042221928954210879345804712799327079745080555023022033466792303459850674857620285874815574802371329773638433227733759575112397461043677209614526192111224394692908806923722840593441994592928018644296349048986244694395012690096195867473674608250003611166034107784830377932943829818771433027171720042248780781157674455524978431249353992937336348052340870315963289849072435291028036614550682468627620637103383883563078732207757705162947919782457146839");
        privatekey[0] = new BigInteger("22745684232701186008671616454591264943229187451754027022853213192409880971046526836318350508302766637233626175463015808748887588443791343847160614146397496272560238085751891980523945566033655183527679658031064808781327084756593369783159204499879010941032960045811093387165963417673844703677882007651984022112834166499361346757332130649719421396226108115011887751081644255141519044255182377725984093821444497930601374471851871641323332674422291298145894813035414179668325370147527644386300821219661212188115733071194714407088111829183414737317982718242614857845490646883934077347762153893825283565593656209107038602483");

        try {

            //rFName = dis.readUTF();
            //int r = dis.read();
            //byte[] r = new byte[256];
            //dis.readFully(r);
            //String r = dis.readUTF();
            String eFName;
            String eLName;
            String eSSN;
            //String m = dis.readUTF();
            System.out.println("Received Encrypted data From Voter: ");
            eFName = dis.readUTF();
            eLName = dis.readUTF();
            eSSN = dis.readUTF();
            System.out.println(eFName);
            System.out.println(eLName);
            System.out.println(eSSN);
            System.out.println("Decrypting Data: ");
            rFName = RSA.decrypt(eFName,privatekey[0],privatekey[1]);
            rLName = RSA.decrypt(eLName,privatekey[0],privatekey[1]);
            rSSN = RSA.decrypt(eSSN,privatekey[0],privatekey[1]);

            System.out.println("Received FName: " + rFName);
            System.out.println("Received LName: " + rLName);
            System.out.println("Received SSN: " + rSSN);

            String db = connectDB(rFName, rLName, rSSN);
            String outm =RSA.encrypt(db,privatekey[0], privatekey[1]);
            dos.writeUTF(outm);
            this.dis.close();
            this.dos.close();
            this.s.close();

            if(db !="000") {
            InetAddress ip = InetAddress.getByName("localhost");
            Socket ctfs = new Socket(ip, 5678);
            BigInteger[] publickey = new BigInteger[2];
            publickey[0] = new BigInteger("8688275128569260821826325505931748414323993316558808141122302113775880596085823597683380791869501264319043546382859751840030466964304556173975662894735217");
            publickey[1] = new BigInteger("17753582724442527811288140733230179391013357165767451089225913120654500722857254143834801755365319179977395860841374637008610486208504344951729123617186569537334486841626064212733609530331613418795139876108629376356313252318547593657475170827843398367536515880912948942247041622900644487376658572915898484164941161260727325552067640163306435279239410888675253723694926047308643149337751414726481431225181009083236990615531581822568483040381258248811876064761108989882492177256526950112223820549016128731788080142279078473357661513655204232539166320510509913961021331102792212834388235587049941805586411066366998952377");
            DataOutputStream dctf = new DataOutputStream(ctfs.getOutputStream());
            String eeFName = RSA.encrypt(rFName, publickey[0], publickey[1]);
            String eeLName = RSA.encrypt(rLName, publickey[0], publickey[1]);
            String eeSSN = RSA.encrypt(rSSN, publickey[0], publickey[1]);
            String eedb = RSA.encrypt(db, publickey[0], publickey[1]);
            dctf.writeUTF(eeFName);
            dctf.writeUTF(eeLName);
            dctf.writeUTF(eeSSN);
            dctf.writeUTF(eedb);
            dctf.close();
            ctfs.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}


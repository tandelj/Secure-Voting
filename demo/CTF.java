package demo;

import java.io.*;
import java.math.BigInteger;
import java.net.*;
import java.sql.*;

// Server class
public class CTF
{
    public static void main(String[] args) throws IOException
    {
        // CLA is listening to Voter on port 1234
        ServerSocket CLAsocket = new ServerSocket(5678);
        ServerSocket Votersocket = new ServerSocket(9012);
        // running infinite loop for getting
        // client request
        while (true)
        {
            Socket CLAs = null;
            Socket Voters = null;

            try{
                // socket object to receive incoming client requests
                CLAs = CLAsocket.accept();
                System.out.println("CLA is sending Data: ");
                // obtaining input and out streams
                DataInputStream dis = new DataInputStream(CLAs.getInputStream());
                DataOutputStream dos = new DataOutputStream(CLAs.getOutputStream());
                System.out.println("Assigning new thread for this CLA Request");
                // create a new thread object
                Thread t = new CLAClient(CLAs, dis, dos);

                // Invoking the start() method
                t.start();
            }
            catch (Exception e){
                CLAs.close();
                e.printStackTrace();
            }
            try
            {
                // socket object to receive incoming client requests
                Voters = Votersocket.accept();
                System.out.println("Voter is sending Vote: ");
                // obtaining input and out streams
                DataInputStream dis = new DataInputStream(Voters.getInputStream());
                DataOutputStream dos = new DataOutputStream(Voters.getOutputStream());
                System.out.println("Assigning new thread for this Voter Request");
                // create a new thread object
                Thread t = new VoterClient(Voters, dis, dos);
                // Invoking the start() method

                t.start();
            }
            catch (Exception e){
                Voters.close();
                e.printStackTrace();
            }

        }
    }
}

// ClientHandler class
class CLAClient extends Thread {

    final DataInputStream dis;
    final DataOutputStream dos;
    final Socket s;


    // Constructor
    public CLAClient(Socket s, DataInputStream dis, DataOutputStream dos) {
        this.s = s;
        this.dis = dis;
        this.dos = dos;
    }
    public void insertToDB(String FName, String LName, String SSN,String ValNo) throws Exception{
        Connection myConn = null;
        myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/CTF?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC","root","12345678");
        Statement my =myConn.createStatement();

        String query1 = "insert into VoterList (FName, LName, SSN, ValNo, Voted) values (?, ?, ?, ?, ?);";
        PreparedStatement pst1 = myConn.prepareStatement(query1);
        pst1.setString(1, FName);
        pst1.setString(2, LName);
        pst1.setString(3, SSN);
        pst1.setString(4, ValNo);
        pst1.setInt(5, 0);
        pst1.execute();
    }
    @Override
    public void run() {
        String rFName;
        String rLName;
        String rSSN;
        String rValNo;
        String toreturn;
        try {
            BigInteger[] privatekey = new BigInteger[2];
            privatekey[0] = new BigInteger("5186956778070490725827198288315541331762271031265650622601024987979541794012230050170887439180262970650579632594342535142168252654372624364147663522497157006210177159575206484433286424574189064136785170951309530803436719815386187521529564560964125528938871900650317585138684464870738955908300215192905985166597649310390633398118521719240663894729103142002674580197566730520181909805212804075082906051795296619336355587217862866915301074975872807147655916358974785644728910464501247807868811156292097500860960572384299904596550258896341216307110693345495105349956029672180383718473289123756713684374076431026851216033");
            privatekey[1] = new BigInteger("17753582724442527811288140733230179391013357165767451089225913120654500722857254143834801755365319179977395860841374637008610486208504344951729123617186569537334486841626064212733609530331613418795139876108629376356313252318547593657475170827843398367536515880912948942247041622900644487376658572915898484164941161260727325552067640163306435279239410888675253723694926047308643149337751414726481431225181009083236990615531581822568483040381258248811876064761108989882492177256526950112223820549016128731788080142279078473357661513655204232539166320510509913961021331102792212834388235587049941805586411066366998952377");
            String eFName;
            String eLName;
            String eValNo;
            String eSSN;
            eFName = dis.readUTF();
            eLName = dis.readUTF();
            eSSN = dis.readUTF();
            eValNo = dis.readUTF();
            System.out.println("Received Encrypted Data form CLA...");
            System.out.println(eFName);
            System.out.println(eLName);
            System.out.println(eValNo);
            System.out.println(eSSN);
            System.out.println("Decrypting Data... ");
            rFName = RSA.decrypt(eFName,privatekey[0],privatekey[1]);
            rLName = RSA.decrypt(eLName,privatekey[0],privatekey[1]);
            rSSN = RSA.decrypt(eSSN,privatekey[0],privatekey[1]);
            rValNo = RSA.decrypt(eValNo,privatekey[0],privatekey[1]);
            System.out.println("Received FName From CLA: " + rFName);
            System.out.println("Received LName From CLA: " + rLName);
            System.out.println("Received SSN From CLA: " + rSSN);
            System.out.println("Received ValNo From CLA: " + rValNo);
            insertToDB(rFName, rLName, rSSN,rValNo);
            this.dis.close();
            this.dos.close();
            this.s.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}

// ClientHandler class
class VoterClient extends Thread {

    final DataInputStream dis;
    final DataOutputStream dos;
    final Socket s;


    // Constructor
    public VoterClient(Socket s, DataInputStream dis, DataOutputStream dos) {
        this.s = s;
        this.dis = dis;
        this.dos = dos;
    }

    String connectDB(String ValNo, char Vote) throws Exception{
        Connection myConn = null;
        myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/CTF?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC","root","12345678");
        Statement my =myConn.createStatement();
        String query1 = "select * from VoterList where ValNo = ? and Voted = 0";
        PreparedStatement pst1 = myConn.prepareStatement(query1);
        pst1.setString(1, ValNo);
        //pst1.setInt(2, );
        //pst1.execute();
        ResultSet rs = pst1.executeQuery();
        if(rs.next()) {
            System.out.println("New Voter");
            String query2 = "update Candidates set VotesReceived = VotesReceived+1 where id = ?; ";
            PreparedStatement pst2 = myConn.prepareStatement(query2);
            pst2.setInt(1, Character.getNumericValue(Vote));
            pst2.execute();
            String query3 = "update VoterList set Voted = 1 where ValNo = ?";
            PreparedStatement pst3 = myConn.prepareStatement(query3);
            pst3.setString(1,ValNo);
            pst3.execute();
            return "111";
        }
        else{
            System.out.println("Old Voter");

            return "000";
        }

    }

    String getResult() throws Exception{
        Connection myConn = null;
        myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/CTF?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC","root","12345678");
        Statement my =myConn.createStatement();
        String query1 = "select Name, VotesReceived from Candidates";
        ResultSet rs = my.executeQuery(query1);
        System.out.println("Candidate Name\t\tNo of Votes");
        String winner;
        String name="";
        int votes=0;
        while(rs.next()){
            System.out.println(rs.getString(1) + "\t\t" +rs.getInt(2));
            if(rs.getInt(2)>votes){
                votes = rs.getInt(2);
                name = rs.getString(1);
            }
        }
        winner = name + " won with " + votes + " votes ";
        //System.out.println(winner);
        return winner;
        //PreparedStatement pst1 = myConn.prepareStatement(query1);
        //pst1.setString(1, ValNo);
    }

    @Override
    public void run() {
        BigInteger[] privatekey = new BigInteger[2];
        privatekey[0] = new BigInteger("5186956778070490725827198288315541331762271031265650622601024987979541794012230050170887439180262970650579632594342535142168252654372624364147663522497157006210177159575206484433286424574189064136785170951309530803436719815386187521529564560964125528938871900650317585138684464870738955908300215192905985166597649310390633398118521719240663894729103142002674580197566730520181909805212804075082906051795296619336355587217862866915301074975872807147655916358974785644728910464501247807868811156292097500860960572384299904596550258896341216307110693345495105349956029672180383718473289123756713684374076431026851216033");
        privatekey[1] = new BigInteger("17753582724442527811288140733230179391013357165767451089225913120654500722857254143834801755365319179977395860841374637008610486208504344951729123617186569537334486841626064212733609530331613418795139876108629376356313252318547593657475170827843398367536515880912948942247041622900644487376658572915898484164941161260727325552067640163306435279239410888675253723694926047308643149337751414726481431225181009083236990615531581822568483040381258248811876064761108989882492177256526950112223820549016128731788080142279078473357661513655204232539166320510509913961021331102792212834388235587049941805586411066366998952377");
        String ValNo;
        char vote;
        String toreturn;
        try {
            String eValNo;
            String evote;
            eValNo = dis.readUTF();
            evote = dis.readUTF();
            System.out.println("Received Encrypted Message from Voter..");
            System.out.println(eValNo);
            System.out.println(evote);
            System.out.println("Decrypting Data..");
            ValNo = RSA.decrypt(eValNo, privatekey[0], privatekey[1]);
            vote = (RSA.decrypt(evote, privatekey[0], privatekey[1])).charAt(0);
            System.out.println("Received ValNo: " + ValNo);
            System.out.println("Received Vote: " +vote);
            if( vote == 'e' || vote == 'E'){
                System.out.println("Voting Ended..");
                toreturn=getResult();
                String etoreturn;
                etoreturn = RSA.encrypt(toreturn,privatekey[0],privatekey[1]);
                dos.writeUTF(etoreturn);
                System.exit(0);
            }
            else {
                toreturn = connectDB(ValNo, vote);
                String etoreturn;
                etoreturn = RSA.encrypt(toreturn,privatekey[0],privatekey[1]);
                dos.writeUTF(etoreturn);
            }
            this.dis.close();
            this.dos.close();
            this.s.close();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}


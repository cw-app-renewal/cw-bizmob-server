package sap.step;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Properties;

import com.sap.conn.jco.AbapException;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoField;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoStructure;
import com.sap.conn.jco.JCoTable;
import com.sap.conn.jco.ext.DestinationDataProvider;


public class StepByStepClient
{
    static String DESTINATION_NAME1 = "ABAP_AS_WITHOUT_POOL";
    static String DESTINATION_NAME2 = "ABAP_AS_WITH_POOL";
    
    static
    {
        Properties connectProperties = new Properties();
        connectProperties.setProperty(DestinationDataProvider.JCO_MSHOST, "192.1.1.149");
        //connectProperties.setProperty(DestinationDataProvider.JCO_MSHOST, "192.1.1.145");
        connectProperties.setProperty(DestinationDataProvider.JCO_SYSNR,  "00");
        //connectProperties.setProperty(DestinationDataProvider.JCO_CLIENT, "210");
        connectProperties.setProperty(DestinationDataProvider.JCO_CLIENT, "100");
        connectProperties.setProperty(DestinationDataProvider.JCO_R3NAME, "COQ");
        //connectProperties.setProperty(DestinationDataProvider.JCO_R3NAME, "COD");
        connectProperties.setProperty(DestinationDataProvider.JCO_GROUP, "TITAN");
        connectProperties.setProperty(DestinationDataProvider.JCO_USER,   "RFCTEST");
        connectProperties.setProperty(DestinationDataProvider.JCO_PASSWD, "qwer1234");
        //connectProperties.setProperty(DestinationDataProvider.JCO_USER_ID,   "WJCSMP02");
        //connectProperties.setProperty(DestinationDataProvider.JCO_PASSWD, "1234567");
        connectProperties.setProperty(DestinationDataProvider.JCO_LANG,   "KO");
        createDestinationDataFile(DESTINATION_NAME1, connectProperties);
        connectProperties.setProperty(DestinationDataProvider.JCO_POOL_CAPACITY, "3");
        connectProperties.setProperty(DestinationDataProvider.JCO_PEAK_LIMIT,    "10");
        createDestinationDataFile(DESTINATION_NAME2, connectProperties);
        
    }
    
    static void createDestinationDataFile(String destinationName, Properties connectProperties)
    {
        File destCfg = new File(destinationName+".jcoDestination");
        try
        {
            FileOutputStream fos = new FileOutputStream(destCfg, false);
            connectProperties.store(fos, "for tests only !");
            fos.close();
        }
        catch (Exception e)
        {
            throw new RuntimeException("Unable to create the destination files", e);
        }
    }
    
    public static void step1Connect() throws JCoException
    {
        JCoDestination destination = JCoDestinationManager.getDestination(DESTINATION_NAME1);
        System.out.println("Attributes:");
        System.out.println(destination.getAttributes());
        System.out.println();
    }
    
    public static void step2ConnectUsingPool() throws JCoException
    {
        JCoDestination destination = JCoDestinationManager.getDestination(DESTINATION_NAME2);
        destination.ping();
        System.out.println("Attributes:");
        System.out.println(destination.getAttributes());
        System.out.println();
    }
    
    public static void step3SimpleCall() throws JCoException
    {
        JCoDestination destination = JCoDestinationManager.getDestination(DESTINATION_NAME2);
        JCoFunction function = destination.getRepository().getFunction("STFC_CONNECTION");
        if (function == null)
            throw new RuntimeException("BAPI_COMPANYCODE_GETLIST not found in SAP.");
        function.getImportParameterList().setValue("REQUTEXT", "Hello SAP");
        
        try
        {
            function.execute(destination);
        }
        catch (AbapException e)
        {
            System.out.println(e.toString());
            return;
        }
        
        System.out.println("STFC_CONNECTION finished:");
        System.out.println(" Echo: " + function.getExportParameterList().getString("ECHOTEXT"));
        System.out.println(" Response: " + function.getExportParameterList().getString("RESPTEXT"));
        System.out.println();
    }
    
    public static void step3WorkWithStructure() throws JCoException
    {
        JCoDestination destination = JCoDestinationManager.getDestination(DESTINATION_NAME2);
        JCoFunction function = destination.getRepository().getFunction("RFC_SYSTEM_INFO");
        if (function == null)
            throw new RuntimeException("BAPI_COMPANYCODE_GETLIST not found in SAP.");
        try
        {
            function.execute(destination);
        }
        catch (AbapException e)
        {
            System.out.println(e.toString());
            return;
        }
        
        JCoStructure exportStructure = function.getExportParameterList().getStructure("RFCSI_EXPORT");
        System.out.println("System info for " + destination.getAttributes().getSystemID() + ":\n");
        for(int i = 0; i < exportStructure.getMetaData().getFieldCount(); i++) 
        {
            System.out.println(exportStructure.getMetaData().getName(i) + ":\t" + exportStructure.getString(i));
        }
        System.out.println();
        
        //JCo still supports the JCoFields, but direct access via getXX is more efficient as field iterator
        System.out.println("The same using field iterator: \nSystem info for " + destination.getAttributes().getSystemID() + ":\n");
        for(JCoField field : exportStructure)
        {
            System.out.println(field.getName() + ":\t" + field.getString());
        }
        System.out.println();
    }
    
    public static void step4WorkWithTable() throws JCoException
    {
        JCoDestination destination = JCoDestinationManager.getDestination(DESTINATION_NAME2);
        JCoFunction function = destination.getRepository().getFunction("BAPI_COMPANYCODE_GETLIST");
        if (function == null)
            throw new RuntimeException("BAPI_COMPANYCODE_GETLIST not found in SAP.");
        try
        {
            function.execute(destination);
        }
        catch(AbapException e)
        {
            System.out.println(e.toString());
            return;
        }
        
        JCoStructure returnStructure = function.getExportParameterList().getStructure("RETURN");
        if (! (returnStructure.getString("TYPE").equals("")||returnStructure.getString("TYPE").equals("S"))  )   
        {
           throw new RuntimeException(returnStructure.getString("MESSAGE"));
        }
        
        JCoTable codes = function.getTableParameterList().getTable("COMPANYCODE_LIST");
        for (int i = 0; i < codes.getNumRows(); i++) 
        {
            codes.setRow(i);
            System.out.println(codes.getString("COMP_CODE") + '\t' + codes.getString("COMP_NAME"));
        }
        codes.firstRow();
        for (int i = 0; i < codes.getNumRows(); i++, codes.nextRow()) 
        {
            function = destination.getRepository().getFunction("BAPI_COMPANYCODE_GETDETAIL");
            if (function == null) 
                throw new RuntimeException("BAPI_COMPANYCODE_GETDETAIL not found in SAP.");
            function.getImportParameterList().setValue("COMPANYCODEID", codes.getString("COMP_CODE"));
            function.getExportParameterList().setActive("COMPANYCODE_ADDRESS",false);
            
            try
            {
                function.execute(destination);
            }
            catch (AbapException e)
            {
                System.out.println(e.toString());
                return;
            }
            returnStructure = function.getExportParameterList().getStructure("RETURN");
            if (! (returnStructure.getString("TYPE").equals("") ||
                   returnStructure.getString("TYPE").equals("S") ||
                   returnStructure.getString("TYPE").equals("W")) ) 
            {
                throw new RuntimeException(returnStructure.getString("MESSAGE"));
            }
            
            JCoStructure detail = function.getExportParameterList().getStructure("COMPANYCODE_DETAIL");
            
            System.out.println(detail.getString("COMP_CODE") + '\t' +
                               detail.getString("COUNTRY") + '\t' +
                               detail.getString("CITY"));
        }
    }
}

    
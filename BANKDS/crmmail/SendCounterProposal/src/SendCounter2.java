import javax.mail.*;
import javax.mail.internet.*;
//import javax.activation.*;
import java.util.*;
import java.io.*;
//import java.net.*;
import java.sql.*;

class SendCounter2 {
	public static void main(String[] args) {
        boolean blnIsException = false;

        //---<---日期------
        Calendar today = Calendar.getInstance();
        long lngYesterday = today.getTimeInMillis()-86400000;
        today.setTimeInMillis(lngYesterday);
        int intyear = today.get(Calendar.YEAR);
        int intmonth = today.get(Calendar.MONTH)+1;
        int intday = today.get(Calendar.DATE);
        int DayOfWeek = today.get(Calendar.DAY_OF_WEEK);//取得今天是星期幾
        String strDate = String.valueOf(intyear)+String.valueOf(intmonth)+String.valueOf(intday);
        String strDateOutput = String.valueOf(intyear) + "/" + String.valueOf(intmonth) + "/" + String.valueOf(intday);
        //System.out.println(strDate);
        //System.out.println(strDateOutput);
        //------日期--->---

        /* 可由檔案得知人次，故取消
        //---<---get counter data------
        String brad = "0";
        String brad1 = "0";
        try {
            URL objURL = new URL("http://192.168.100.34:9080/ProInput/" + strDate);
            BufferedReader br = new BufferedReader(new InputStreamReader(objURL.openStream()));
            String strHTML = "";
            do {
                strHTML = br.readLine();
                if (strHTML != null){
                    brad = strHTML;
                }
            } while (strHTML != null);
            br.close();
            br = null;
            objURL = null;


            URL objURL1 = new URL("http://192.168.100.34:9080/ProInput/" + strDate + "_home");
            BufferedReader br1 = new BufferedReader(new InputStreamReader(objURL1.openStream()));
            String strHTML1 = "";
            do {
                strHTML1 = br1.readLine();
                if (strHTML1 != null){
                    brad1 = strHTML1;
                }
            } while (strHTML1 != null);
            br1.close();
            br1 = null;
            objURL1 = null;	
        }
        catch (Exception e) {
            //---<---log------
            writeLog(strDateOutput + ": get url counter's exception: " + e.toString());
            //------log--->---
        }
        //------get counter data--->---
        */

        //---<---get mail address(to) & content------
        //mail to 人員改由DB中擷取資料	Wen 20110414
        String strMailTo = "";	//收件者mail
        String strContent = "";
/*
        try {
            FileReader fr = new FileReader("mailto.txt");
            BufferedReader br = new BufferedReader(fr);
            strMailTo = br.readLine();
            strContent = br.readLine();
            br.close();
            fr.close();
            br = null;
            fr = null;
        }
        catch (Exception e) {
            //---<---log------
            writeLog(strDateOutput + ": get mail address's exception: " + e.toString());
            //------log--->---
            blnIsException = true;
        }
*/
        //-------get mail address(to) & content------

        //---<---get db data------
        String strHomePageCountTimes = "";      // 首頁人次〈國泰人壽業務〉
        String strHomePageCountPeople = "";      // 首頁人數〈國泰人壽業務〉
        String strInsertCountTimesT = "";      // 新增傳統型人次〈國泰人壽業務〉
        String strInsertCountPeopleT = "";      // 新增傳統型人數〈國泰人壽業務〉
        String strInsertCountTimesI = "";      // 新增投資型人次〈國泰人壽業務〉
        String strInsertCountPeopleI = "";      // 新增投資型人數〈國泰人壽業務〉
        String strPrintCountTimes = "";      // 列印人次〈國泰人壽業務〉
        String strPrintCountPeople = "";      // 列印人數〈國泰人壽業務〉
		
		String strHomePageWeekCountTimes = "";      // 首頁人次〈國泰人壽業務〉per week
		double avgHomePageWeekCountTimes = 0.0;      // 平均首頁人次〈國泰人壽業務〉per week
        String strHomePageWeekCountPeople = "";      // 首頁人數〈國泰人壽業務〉per week
		double avgHomePageWeekCountPeople = 0.0;      // 平均首頁人數〈國泰人壽業務〉per week
        String strInsertWeekCountTimesT = "";      // 新增傳統型人次〈國泰人壽業務〉per week
        double avgInsertWeekCountTimesT = 0.0;      // 平均新增傳統型人次〈國泰人壽業務〉per week
		String strInsertWeekCountPeopleT = "";      // 新增傳統型人數〈國泰人壽業務〉per week
        double avgInsertWeekCountPeopleT = 0.0;      // 平均新增傳統型人數〈國泰人壽業務〉per week
		String strInsertWeekCountTimesI = "";      // 新增投資型人次〈國泰人壽業務〉per week
        double avgInsertWeekCountTimesI = 0.0;      // 平均新增投資型人次〈國泰人壽業務〉per week
		String strInsertWeekCountPeopleI = "";      // 新增投資型人數〈國泰人壽業務〉per week
        double avgInsertWeekCountPeopleI = 0.0;      // 平均新增投資型人數〈國泰人壽業務〉per week
		String strPrintWeekCountTimes = "";      // 列印人次〈國泰人壽業務〉per week
        double avgPrintWeekCountTimes = 0.0;      // 平均列印人次〈國泰人壽業務〉per week
		String strPrintWeekCountPeople = "";      // 列印人數〈國泰人壽業務〉per week
		double avgPrintWeekCountPeople = 0.0;      // 平均列印人數〈國泰人壽業務〉per week
		
		String strHomePageMonthCountTimes = "";      // 首頁人次〈國泰人壽業務〉per month
		double avgHomePageMonthCountTimes = 0.0;      // 平均首頁人次〈國泰人壽業務〉per month
        String strHomePageMonthCountPeople = "";      // 首頁人數〈國泰人壽業務〉per month
		double avgHomePageMonthCountPeople = 0.0;      // 平均首頁人數〈國泰人壽業務〉per month
        String strInsertMonthCountTimesT = "";      // 新增傳統型人次〈國泰人壽業務〉per month
		double avgInsertMonthCountTimesT = 0.0;      // 平均新增傳統型人次〈國泰人壽業務〉per month
        String strInsertMonthCountPeopleT = "";      // 新增傳統型人數〈國泰人壽業務〉per month
		double avgInsertMonthCountPeopleT = 0.0;      // 平均新增傳統型人數〈國泰人壽業務〉per month
        String strInsertMonthCountTimesI = "";      // 新增投資型人次〈國泰人壽業務〉per month
		double avgInsertMonthCountTimesI = 0.0;      // 平均新增投資型人次〈國泰人壽業務〉per month
        String strInsertMonthCountPeopleI = "";      // 新增投資型人數〈國泰人壽業務〉per month
		double avgInsertMonthCountPeopleI = 0.0;      // 平均新增投資型人數〈國泰人壽業務〉per month
        String strPrintMonthCountTimes = "";      // 列印人次〈國泰人壽業務〉per month
		double avgPrintMonthCountTimes = 0.0;      // 平均列印人次〈國泰人壽業務〉per month
        String strPrintMonthCountPeople = "";      // 列印人數〈國泰人壽業務〉per month
		double avgPrintMonthCountPeople = 0.0;      // 平均列印人數〈國泰人壽業務〉per month

        String strHomePageCountTimesBank = "";      // 首頁人次〈國泰世華理專〉
		double avgHomePageCountTimesBank = 0.0;      // 平均首頁人次〈國泰世華理專〉
        String strHomePageCountPeopleBank = "";      // 首頁人數〈國泰世華理專〉
		double avgHomePageCountPeopleBank = 0.0;      // 平均首頁人數〈國泰世華理專〉
        String strInsertCountTimesTBank = "";      // 新增傳統型人次〈國泰世華理專〉
		double avgInsertCountTimesTBank = 0.0;      // 平均新增傳統型人次〈國泰世華理專〉
        String strInsertCountPeopleTBank = "";      // 新增傳統型人數〈國泰世華理專〉
		double avgInsertCountPeopleTBank = 0.0;      // 平均新增傳統型人數〈國泰世華理專〉
        String strInsertCountTimesIBank = "";      // 新增投資型人次〈國泰世華理專〉
		double avgInsertCountTimesIBank = 0.0;      // 平均新增投資型人次〈國泰世華理專〉
        String strInsertCountPeopleIBank = "";      // 新增投資型人數〈國泰世華理專〉
		double avgInsertCountPeopleIBank = 0.0;      // 平均新增投資型人數〈國泰世華理專〉
        String strPrintCountTimesBank = "";      // 列印人次〈國泰世華理專〉
		double avgPrintCountTimesBank = 0.0;      // 平均列印人次〈國泰世華理專〉
        String strPrintCountPeopleBank = "";      // 列印人數〈國泰世華理專〉
		double avgPrintCountPeopleBank = 0.0;      // 平均列印人數〈國泰世華理專〉

        String strHomePageCountName = "";      // 首頁姓名
        String strInsertCountNameT = "";      // 新增傳統型姓名
        String strInsertCountNameI = "";      // 新增投資型姓名
        String strPrintCountName = "";      // 列印姓名
		
		//String strEmployeeAllCount = "";		//公司專招+展業員工數
		//String strEmployee_1_Count = "";		//公司專招員工數(沒用到)
		//String strEmployee_2_Count = "";		//公司展業員工數(沒用到)
		
		int EmployeeCount = 1;			//公司專招+展業員工數
		int dayCount = 1;				//每週或每月實際計算天數(扣掉週六日)
		
		boolean debug = false;

        try {
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
        }
        catch (Exception e1) {
            System.out.println(e1.toString());
            blnIsException = true;
        }

        Connection objConn = null;
        Connection objConnBankUser = null;
        try {
            //---<---本機------
            objConn = DriverManager.getConnection("jdbc:odbc:CFHCDW", "suguser", "suguser");
            //------本機--->---

            //---<---ws900xxxx------
            //objConn = DriverManager.getConnection("jdbc:odbc:P1", "suguser", "suguser");
            //------ws900xxxx--->---
			
            //---<---get mail address(to) & content------
            //mail to 人員改由DB中擷取資料	Wen 20110414
            PreparedStatement mailPstmt = objConn.prepareStatement("SELECT * FROM INSDB.DAILYSTATS_RECEIVER WHERE DAILYX = '網路版商品建議書' WITH UR");
            ResultSet mailRs = mailPstmt.executeQuery();
            int mailCounter = 0;
            while(mailRs.next()){
            	if(mailCounter == 0){
            		strMailTo = mailRs.getString("RC_MAIL"); 
            	}else{
            		strMailTo = strMailTo + "," + mailRs.getString("RC_MAIL"); 
            	}
            	mailCounter++;
            }
            if(debug)	writeLog("strMailTo: "+strMailTo);
            //-------get mail address(to) & content------
            
			//---<---查詢員工人數------
            // 查詢公司專招+展業人數
			if(debug)	writeLog("查詢公司專招+展業人數 START");
            //objPStmt = objConn.prepareStatement("select sum(case substr(cedvno,3,1) when '1' then 1 when '4' then 1 else 0 end) as all, sum(case substr(cedvno,3,1) when '1' then 1 else 0 end) as a, sum(case substr(cedvno,3,1) when '4' then 1 else 0 end) as b from insdb.web_basic with ur");
			PreparedStatement objPStmt = objConn.prepareStatement("select sum(case substr(cedvno,3,1) when '1' then 1 when '4' then 1 else 0 end)  from insdb.web_basic with ur");
            ResultSet objRS = objPStmt.executeQuery();
            while (objRS.next()) {
//				EmployeeCount = Integer.parseInt(objRS.getString(1));	//Wen：不知道當初為什麼要寫得這麼複雜
            	EmployeeCount = objRS.getInt(1);
                //strEmployee_1_Count = objRS.getString(2);
				//strEmployee_2_Count = objRS.getString(3);
            }
			if(debug)	writeLog("查詢公司專招+展業人數 END: "+EmployeeCount);
			//------查詢員工人數--->---

            //---<---首頁------
            // 首頁〈國泰人壽業務〉每天
			if(debug)	writeLog("首頁〈國泰人壽業務〉每天 START");
            objPStmt = objConn.prepareStatement("select sum(cnt), count(distinct agidno) from insdb.sug_count1 a inner join insdb.web_basic b on a.agidno = b.ceempid where substr(b.cedvno,3,1) in ('1','4')  and agidno != '' and in_day=(current date - 1 days) and isinv in ('I', 'T', 'Q', 'CI', 'CT', 'CQ', 'CB') with ur");
            objRS = objPStmt.executeQuery();
            while (objRS.next()) {
                //strHomePageCountTimes = objRS.getString(1) == null ? "0" : objRS.getString(1);	//Wen 20110221
            	strHomePageCountTimes = String.valueOf(objRS.getInt(1));	//使用getString()會有SQLException: No data found問題，故改成getInt()Wen 20110221
                strHomePageCountPeople = objRS.getString(2);
            }
			if(debug)	writeLog("首頁〈國泰人壽業務〉每天 END");
            
            //首頁〈國泰人壽業務〉每週
			if(debug)	writeLog("首頁〈國泰人壽業務〉每週 START");
            objPStmt = objConn.prepareStatement("select sum(a.cnt), count(distinct a.agidno), count(distinct a.in_day) from insdb.sug_count1 a inner join insdb.web_basic b on a.agidno = b.ceempid where substr(b.cedvno,3,1) in ('1','4') and a.agidno != '' and DAYOFWEEK_ISO(a.in_day) <6  and a.in_day >= (current date -  7 days) and a.in_day < current date and isinv in ('I', 'T', 'Q', 'CI', 'CT', 'CQ', 'CB') with ur");
            objRS = objPStmt.executeQuery();
            while (objRS.next()) {
                //strHomePageWeekCountTimes = objRS.getString(1) == null ? "0" : objRS.getString(1);	//Wen 20110221
            	strHomePageWeekCountTimes = String.valueOf(objRS.getInt(1));	//Wen 20110225
                strHomePageWeekCountPeople = objRS.getString(2);
				dayCount = Integer.parseInt( objRS.getString(3) );
            }
			if(debug)	writeLog("首頁〈國泰人壽業務〉每週 END");
			avgHomePageWeekCountTimes = round(Double.parseDouble(strHomePageWeekCountTimes)/dayCount);
			avgHomePageWeekCountPeople = round(Double.parseDouble(strHomePageWeekCountPeople)/EmployeeCount*100);
    		
            //首頁〈國泰人壽業務〉每月
			if(debug)	writeLog("首頁〈國泰人壽業務〉每月 START");
            objPStmt = objConn.prepareStatement("select sum(a.cnt), count(distinct a.agidno), count(distinct a.in_day) from insdb.sug_count1 a inner join insdb.web_basic b on a.agidno = b.ceempid where substr(b.cedvno,3,1) in ('1','4') and a.agidno != '' and DAYOFWEEK_ISO(a.in_day) <6  and a.in_day >= (current date - 30 days) and a.in_day < current date and isinv in ('I', 'T', 'Q', 'CI', 'CT', 'CQ', 'CB') with ur");
            objRS = objPStmt.executeQuery();
            while (objRS.next()) {
                //strHomePageMonthCountTimes = objRS.getString(1);
            	strHomePageMonthCountTimes = String.valueOf(objRS.getInt(1));	//Wen 20110225
                strHomePageMonthCountPeople = objRS.getString(2);
				dayCount = Integer.parseInt( objRS.getString(3) );
            }
			if(debug)	writeLog("首頁〈國泰人壽業務〉每月 END");
			avgHomePageMonthCountTimes = round(Double.parseDouble(strHomePageMonthCountTimes)/dayCount);
			avgHomePageMonthCountPeople = round(Double.parseDouble(strHomePageMonthCountPeople)/EmployeeCount*100);
            
            /*
            // 首頁〈國泰世華理專〉
            objPStmt = objConn.prepareStatement("select sum(cnt), count(distinct agidno) from insdb.sug_count1 where agidno != '' and in_day=(current date - 1 days) and (isinv='BI' or isinv='BT' or isinv='BQ')");
            objRS = objPStmt.executeQuery();
            while (objRS.next()) {
                strHomePageCountTimesBank = objRS.getString(1);
                strHomePageCountPeopleBank = objRS.getString(2);
            }

            // 首頁〈國泰世華理專〉：姓名
            objPStmt = objConn.prepareStatement("select sum(a.cnt) as SUM, b.name from insdb.sug_count1 a, insdb.bnk_invest_ser b where a.agidno=b.idno and a.agidno != '' and a.in_day=(current date - 1 days) and (a.isinv='BI' or a.isinv='BT' or a.isinv='BQ') group by b.name");
            objRS = objPStmt.executeQuery();
            while (objRS.next()) {
                strHomePageCountName += " " + objRS.getString("NAME") + objRS.getString("SUM") + "次 ";
            }
            */
            //------首頁--->---

            //---<---新增傳統(CT)、利變(CB)型------
            // 新增傳統(CT)、利變(CB)型〈國泰人壽業務〉
			if(debug)	writeLog("新增傳統(CT)、利變(CB)型〈國泰人壽業務〉START");
            objPStmt = objConn.prepareStatement("select sum(cnt), count(*) from insdb.sug_count a inner join insdb.web_basic b on a.agidno = b.ceempid where substr(b.cedvno,3,1) in ('1','4') and agidno!='' and in_day=(current date - 1 days) and isinv in ('CT', 'CB') with ur");
            objRS = objPStmt.executeQuery();
            while (objRS.next()) {
                //strInsertCountTimesT = objRS.getString(1);
            	strInsertCountTimesT = String.valueOf(objRS.getInt(1));	//Wen 20110225
                strInsertCountPeopleT = objRS.getString(2);
            }
			if(debug)	writeLog("新增傳統(CT)、利變(CB)型〈國泰人壽業務〉END");
            
            // 新增傳統(CT)、利變(CB)型〈國泰人壽業務〉每週
			if(debug)	writeLog("新增傳統(CT)、利變(CB)型〈國泰人壽業務〉每週 START");
            objPStmt = objConn.prepareStatement("select sum(a.cnt), count(distinct a.agidno), count(distinct a.in_day) from insdb.sug_count a inner join insdb.web_basic b on a.agidno = b.ceempid where substr(b.cedvno,3,1) in ('1','4') and a.agidno!='' and DAYOFWEEK_ISO(a.in_day) <6 and a.in_day >= (current date -  7 days) and a.in_day < current date and isinv in ('T', 'CT', 'CB') with ur");
			objRS = objPStmt.executeQuery();
			while (objRS.next()) {
				//strInsertWeekCountTimesT = objRS.getString(1);
				strInsertWeekCountTimesT = String.valueOf(objRS.getInt(1));	//Wen 20110225
				strInsertWeekCountPeopleT = objRS.getString(2);
				dayCount = Integer.parseInt( objRS.getString(3) );
			}
			if(debug)	writeLog("新增傳統(CT)、利變(CB)型〈國泰人壽業務〉每週 END");
			avgInsertWeekCountTimesT = round(Double.parseDouble(strInsertWeekCountTimesT)/dayCount);
			avgInsertWeekCountPeopleT = round(Double.parseDouble(strInsertWeekCountPeopleT)/EmployeeCount*100);
            
            // 新增傳統(CT)、利變(CB)型〈國泰人壽業務〉每月
			if(debug)	writeLog("新增傳統(CT)、利變(CB)型〈國泰人壽業務〉每月 START");
            objPStmt = objConn.prepareStatement("select sum(a.cnt), count(distinct a.agidno), count(distinct a.in_day) from insdb.sug_count a inner join insdb.web_basic b on a.agidno = b.ceempid where substr(b.cedvno,3,1) in ('1','4') and a.agidno!='' and DAYOFWEEK_ISO(a.in_day) <6  and a.in_day >= (current date - 30 days) and a.in_day < current date and isinv in ('T', 'CT', 'CB') with ur");
			objRS = objPStmt.executeQuery();
			while (objRS.next()) {
				//strInsertMonthCountTimesT = objRS.getString(1);
				strInsertMonthCountTimesT = String.valueOf(objRS.getInt(1));	//Wen 20110225
				strInsertMonthCountPeopleT = objRS.getString(2);
				dayCount = Integer.parseInt( objRS.getString(3) );
			}
			if(debug)	writeLog("新增傳統(CT)、利變(CB)型〈國泰人壽業務〉每月 END");
			avgInsertMonthCountTimesT = round(Double.parseDouble(strInsertMonthCountTimesT)/dayCount);
			avgInsertMonthCountPeopleT = round(Double.parseDouble(strInsertMonthCountPeopleT)/EmployeeCount*100);

            /*
            // 新增傳統型〈國泰世華理專〉
            objPStmt = objConn.prepareStatement("select sum(cnt), count(*) from insdb.sug_count where agidno!='' and in_day=(current date - 1 days) and isinv='BT'");
            objRS = objPStmt.executeQuery();
            while (objRS.next()) {
                strInsertCountTimesTBank = objRS.getString(1);
                strInsertCountPeopleTBank = objRS.getString(2);
            }

            // 新增傳統型〈國泰世華理專〉：姓名
            objPStmt = objConn.prepareStatement("select sum(a.cnt) as SUM, b.name from insdb.sug_count a, insdb.bnk_invest_ser b where a.agidno=b.idno and a.agidno!='' and a.in_day=(current date - 1 days) and a.isinv='BT' group by b.name");
            objRS = objPStmt.executeQuery();
            while (objRS.next()) {
                strInsertCountNameT += " " + objRS.getString("NAME") + objRS.getString("SUM") + "次 ";
            }
            */
            //------新增傳統型--->---

            //---<---新增投資型------
            // 新增投資型〈國泰人壽業務〉
			if(debug)	writeLog("新增投資型〈國泰人壽業務〉 START");
            objPStmt = objConn.prepareStatement("select sum(cnt), count(*) from insdb.sug_count a inner join insdb.web_basic b on a.agidno = b.ceempid where substr(b.cedvno,3,1) in ('1','4') and agidno!='' and in_day=(current date - 1 days) and isinv='CI' with ur");
            objRS = objPStmt.executeQuery();
            while (objRS.next()) {
                //strInsertCountTimesI = objRS.getString(1);
                strInsertCountTimesI = String.valueOf(objRS.getInt(1));	//Wen 20110225
                strInsertCountPeopleI = objRS.getString(2);
            }
			if(debug)	writeLog("新增投資型〈國泰人壽業務〉 END");
            
            // 新增投資型〈國泰人壽業務〉每週
			if(debug)	writeLog("新增投資型〈國泰人壽業務〉每週  START");
            objPStmt = objConn.prepareStatement("select sum(a.cnt), count(distinct a.agidno), count(distinct a.in_day) from insdb.sug_count a inner join insdb.web_basic b on a.agidno = b.ceempid where substr(b.cedvno,3,1) in ('1','4') and a.agidno!='' and DAYOFWEEK_ISO(a.in_day) <6  and a.in_day >= (current date -  7 days) and a.in_day < current date and isinv in ('I', 'CI') with ur");
			objRS = objPStmt.executeQuery();
			while (objRS.next()) {
				//strInsertWeekCountTimesI = objRS.getString(1);
				strInsertWeekCountTimesI = String.valueOf(objRS.getInt(1));	//Wen 20110225
				strInsertWeekCountPeopleI = objRS.getString(2);
				dayCount = Integer.parseInt( objRS.getString(3) );
			}
			if(debug)	writeLog("新增投資型〈國泰人壽業務〉每週  END");
			avgInsertWeekCountTimesI = round(Double.parseDouble(strInsertWeekCountTimesI)/dayCount);
			avgInsertWeekCountPeopleI = round(Double.parseDouble(strInsertWeekCountPeopleI)/EmployeeCount*100);
            
            // 新增投資型〈國泰人壽業務〉每月
			if(debug)	writeLog("新增投資型〈國泰人壽業務〉每月  START");
            objPStmt = objConn.prepareStatement("select sum(a.cnt), count(distinct a.agidno), count(distinct a.in_day) from insdb.sug_count a inner join insdb.web_basic b on a.agidno = b.ceempid where substr(b.cedvno,3,1) in ('1','4') and a.agidno!='' and DAYOFWEEK_ISO(a.in_day) <6  and a.in_day >= (current date - 30 days) and a.in_day < current date and isinv in ('I', 'CI') with ur");
			objRS = objPStmt.executeQuery();
			while (objRS.next()) {
				//strInsertMonthCountTimesI = objRS.getString(1);
				strInsertMonthCountTimesI = String.valueOf(objRS.getInt(1));	//Wen 20110225
				strInsertMonthCountPeopleI = objRS.getString(2);
				dayCount = Integer.parseInt( objRS.getString(3) );
			}
			if(debug)	writeLog("新增投資型〈國泰人壽業務〉每月 END");
			avgInsertMonthCountTimesI = round(Double.parseDouble(strInsertMonthCountTimesI)/dayCount);
			avgInsertMonthCountPeopleI = round(Double.parseDouble(strInsertMonthCountPeopleI)/EmployeeCount*100);

            /*
            // 新增投資型〈國泰世華理專〉
            objPStmt = objConn.prepareStatement("select sum(cnt), count(*) from insdb.sug_count where agidno!='' and in_day=(current date - 1 days) and isinv='BI'");
            objRS = objPStmt.executeQuery();
            while (objRS.next()) {
                strInsertCountTimesIBank = objRS.getString(1);
                strInsertCountPeopleIBank = objRS.getString(2);
            }

            // 新增投資型〈國泰世華理專〉：姓名
            objPStmt = objConn.prepareStatement("select sum(a.cnt) as SUM, b.name from insdb.sug_count a, insdb.bnk_invest_ser b where a.agidno=b.idno and a.agidno!='' and a.in_day=(current date - 1 days) and a.isinv='BI' group by b.name");
            objRS = objPStmt.executeQuery();
            while (objRS.next()) {
                strInsertCountNameI += " " + objRS.getString("NAME") + objRS.getString("SUM") + "次 ";
            }
            */
            //------新增投資型--->---

            //---<---列印------
            // 列印〈國泰人壽業務〉
			//CT：國壽傳統、CB：國壽利變、CI：國壽投資、CC：國壽回傳保校
			if(debug)	writeLog("列印〈國泰人壽業務〉  START");
            Calendar objCal1 = Calendar.getInstance();
            Calendar objCal2 = Calendar.getInstance();
            objCal1.add(Calendar.DATE, -1);
            String strPrintBeginTime = objCal1.get(Calendar.YEAR) + "-" + (objCal1.get(Calendar.MONTH) + 1) + "-" + objCal1.get(Calendar.DATE) + " 00:00:00";
            String strPrintEndTime = objCal2.get(Calendar.YEAR) + "-" + (objCal2.get(Calendar.MONTH) + 1) + "-" + objCal2.get(Calendar.DATE) + " 00:00:00";
            //objPStmt = objConn.prepareStatement("select count(*), count(distinct agidno) from insdb.sug_pcount where agidno != '' and pdate between '" + strPrintBeginTime + "' and '" + strPrintEndTime + "' and (isinv='T' or isinv='I' or isinv='C') with ur");
			objPStmt = objConn.prepareStatement("select count(*), count(distinct agidno) from insdb.sug_pcount  a inner join insdb.web_basic b on a.agidno = b.ceempid where substr(b.cedvno,3,1) in ('1','4') and  agidno != '' and date(pdate) = (current date - 1 days) and isinv in ('T', 'I', 'C', 'CT', 'CB', 'CI', 'CC') with ur");
            objRS = objPStmt.executeQuery();
            while (objRS.next()) {
                strPrintCountTimes = objRS.getString(1);
                strPrintCountPeople = objRS.getString(2);
            }
            if(debug)	writeLog("列印〈國泰人壽業務〉  END");
			
            // 列印〈國泰人壽業務〉每週
			if(debug)	writeLog("列印〈國泰人壽業務〉每週  START");
            objPStmt = objConn.prepareStatement("select count(*), count(distinct a.agidno), count(distinct date(a.pdate)) from insdb.sug_pcount a inner join insdb.web_basic b on a.agidno = b.ceempid where substr(b.cedvno,3,1) in ('1','4') and a.agidno != '' and DAYOFWEEK_ISO(a.pdate) <6  and date(a.pdate) >= (current date -  7 day) and date(a.pdate) < current date and isinv in ('T', 'I', 'C', 'CT', 'CB', 'CI', 'CC') with ur");
			objRS = objPStmt.executeQuery();
			while (objRS.next()) {
				strPrintWeekCountTimes = objRS.getString(1);
				strPrintWeekCountPeople = objRS.getString(2);
				dayCount = Integer.parseInt( objRS.getString(3) );
			}
			if(debug)	writeLog("列印〈國泰人壽業務〉每週  END");
			avgPrintWeekCountTimes = round(Double.parseDouble(strPrintWeekCountTimes)/dayCount);
			avgPrintWeekCountPeople = round(Double.parseDouble(strPrintWeekCountPeople)/EmployeeCount*100);
            
            // 列印〈國泰人壽業務〉每月
			if(debug)	writeLog("列印〈國泰人壽業務〉每月  START");
			objPStmt = objConn.prepareStatement("select count(*), count(distinct a.agidno), count(distinct date(a.pdate)) from insdb.sug_pcount a inner join insdb.web_basic b on a.agidno = b.ceempid where substr(b.cedvno,3,1) in ('1','4') and a.agidno != '' and DAYOFWEEK_ISO(a.pdate) <6  and date(a.pdate) >= (current date - 30 day) and date(a.pdate) < current date and isinv in ('T', 'I', 'C', 'CT', 'CB', 'CI', 'CC') with ur");
			objRS = objPStmt.executeQuery();
			while (objRS.next()) {
				strPrintMonthCountTimes = objRS.getString(1);
				strPrintMonthCountPeople = objRS.getString(2);
				dayCount = Integer.parseInt( objRS.getString(3) );
			}
			if(debug)	writeLog("列印〈國泰人壽業務〉每月  END");
			avgPrintMonthCountTimes = round(Double.parseDouble(strPrintMonthCountTimes)/dayCount);
			avgPrintMonthCountPeople = round(Double.parseDouble(strPrintMonthCountPeople)/EmployeeCount*100);

            /*
            // 列印〈國泰世華理專〉
            strPrintBeginTime = objCal1.get(Calendar.YEAR) + "-" + (objCal1.get(Calendar.MONTH) + 1) + "-" + objCal1.get(Calendar.DATE) + " 00:00:00";
            strPrintEndTime = objCal2.get(Calendar.YEAR) + "-" + (objCal2.get(Calendar.MONTH) + 1) + "-" + objCal2.get(Calendar.DATE) + " 00:00:00";
            objPStmt = objConn.prepareStatement("select count(*), count(distinct agidno) from insdb.sug_pcount where agidno != '' and pdate between '" + strPrintBeginTime + "' and '" + strPrintEndTime + "' and (isinv='BT' or isinv='BI' or isinv='BC')");
            objRS = objPStmt.executeQuery();
            while (objRS.next()) {
                strPrintCountTimesBank = objRS.getString(1);
                strPrintCountPeopleBank = objRS.getString(2);
            }

            // 列印〈國泰世華理專〉：姓名
            strPrintBeginTime = objCal1.get(Calendar.YEAR) + "-" + (objCal1.get(Calendar.MONTH) + 1) + "-" + objCal1.get(Calendar.DATE) + " 00:00:00";
            strPrintEndTime = objCal2.get(Calendar.YEAR) + "-" + (objCal2.get(Calendar.MONTH) + 1) + "-" + objCal2.get(Calendar.DATE) + " 00:00:00";
            objPStmt = objConn.prepareStatement("select count(agidno) as SUM, b.name from insdb.sug_pcount a, insdb.bnk_invest_ser b where a.agidno=b.idno and a.agidno != '' and a.pdate between '" + strPrintBeginTime + "' and '" + strPrintEndTime + "' and (a.isinv='BT' or a.isinv='BI' or a.isinv='BC') group by b.name");
            objRS = objPStmt.executeQuery();
            while (objRS.next()) {
                strPrintCountName += " " + objRS.getString("NAME") + objRS.getString("SUM") + "次 ";
            }
            */
            //------列印--->---
			
			//給經理用的日統計數字 信件寄出前將每日的使用人數人次資料寫入INSDB.DAILYX_CRMDAY	Wen 20110413
			objPStmt = objConn.prepareStatement("INSERT INTO INSDB.DAILYX_CRMDAY (COUNT_DATE, MAINITEM, ITEMNAME, CEDVNO, NUMBER_PEOPLE, PEOPLE) VALUES (current date, '網路版商品建議書1', '建議書使用量', '0', ?, ?)");
			objPStmt.setInt(1, Integer.parseInt(strHomePageCountPeople));
			objPStmt.setInt(2, Integer.parseInt(strHomePageCountTimes));
			objPStmt.execute();
			
			objPStmt = objConn.prepareStatement("INSERT INTO INSDB.DAILYX_CRMDAY (COUNT_DATE, MAINITEM, ITEMNAME, CEDVNO, NUMBER_PEOPLE, PEOPLE) VALUES (current date, '網路版商品建議書2', '編輯傳統型', '0', ?, ?)");
			objPStmt.setInt(1, Integer.parseInt(strInsertCountPeopleT));
			objPStmt.setInt(2, Integer.parseInt(strInsertCountTimesT));
			objPStmt.execute();
			
			objPStmt = objConn.prepareStatement("INSERT INTO INSDB.DAILYX_CRMDAY (COUNT_DATE, MAINITEM, ITEMNAME, CEDVNO, NUMBER_PEOPLE, PEOPLE) VALUES (current date, '網路版商品建議書3', '編輯投資型', '0', ?, ?)");
			objPStmt.setInt(1, Integer.parseInt(strInsertCountPeopleI));
			objPStmt.setInt(2, Integer.parseInt(strInsertCountTimesI));
			objPStmt.execute();
			
			objPStmt = objConn.prepareStatement("INSERT INTO INSDB.DAILYX_CRMDAY (COUNT_DATE, MAINITEM, ITEMNAME, CEDVNO, NUMBER_PEOPLE, PEOPLE) VALUES (current date, '網路版商品建議書4', '列印建議書', '0', ?, ?)");
			objPStmt.setInt(1, Integer.parseInt(strPrintCountPeople));
			objPStmt.setInt(2, Integer.parseInt(strPrintCountTimes));
			objPStmt.execute();
			//
			
            objRS.close();
            objPStmt.close();
            objConn.close();
            objPStmt = null;
            objRS = null;
            objConn = null;
        }
        catch (Exception e) {
            //---<---log------
            writeLog(strDateOutput + ": get db counter's exception: " + e.toString());
            //------log--->---
            blnIsException = true;
        }
        finally {
            try {
                if (objConn != null) {
                    objConn.close();
                    objConn = null;
                }
            }
            catch (Exception e3) {
                System.out.println(e3.toString());
                blnIsException = true;
            }
        }
        //------get db data--->---

//      String mailserver   = "cathaylife.com.tw";
        String mailserver = "sendmail.cathaylife.com.tw";	//Wen modify 2009/12/11
        String From         = "crm@cathaylife.com.tw";
        String to = strMailTo;
        //String to           = "brad1029@cathaylife.com.tw,yanshang@cathaylife.com.tw,conee@cathaylife.com.tw,cctsai@cathaylife.com.tw,aileen@cathaylife.com.tw";
        //String to           = "brad1029@cathaylife.com.tw";
        //String cc = "brad1029@cathaylife.com.tw";
        //String bcc = "brad1029@cathaylife.com.tw";
        String Subject      = strDateOutput+" 網路版商品建議書使用狀況";
        String messageText  = "<< " + strDateOutput + " 網路版商品建議書使用狀況 >>\n";
        messageText += "--國泰人壽業務--建議書使用率：\n";
				messageText += "本日：" + strHomePageCountTimes + " 人次； " + strHomePageCountPeople + "人數。\n";
				messageText += "本週總計：" + strHomePageWeekCountTimes + " 人次； " + strHomePageWeekCountPeople + "人數。\n";
				messageText += "本週平均：" + avgHomePageWeekCountTimes + " 人次； " + avgHomePageWeekCountPeople + "% 使用人數比例。\n";
    		messageText += "本月總計：" + strHomePageMonthCountTimes + " 人次； " + strHomePageMonthCountPeople + "人數。\n";
				messageText += "本月平均：" + avgHomePageMonthCountTimes + " 人次； " + avgHomePageMonthCountPeople + "% 使用人數比例。\n\n";
    
				messageText += "--國泰人壽業務--編輯傳統型：\n";
				messageText += "本日：" + strInsertCountTimesT + " 人次； " + strInsertCountPeopleT + "人數。\n";
    		messageText += "本週總計：" + strInsertWeekCountTimesT + " 人次； " + strInsertWeekCountPeopleT + "人數。\n";
				messageText += "本週平均：" + avgInsertWeekCountTimesT + " 人次； " + avgInsertWeekCountPeopleT + "% 使用人數比例。\n";
				messageText += "本月總計：" + strInsertMonthCountTimesT + " 人次； " + strInsertMonthCountPeopleT + "人數。\n";
				messageText += "本月平均：" + avgInsertMonthCountTimesT + " 人次； " + avgInsertMonthCountPeopleT + "% 使用人數比例。\n\n";
		    
				messageText += "--國泰人壽業務--編輯投資型：\n";
				messageText += "本日：" + strInsertCountTimesI + " 人次； " + strInsertCountPeopleI + "人數。\n";
				messageText += "本週總計：" + strInsertWeekCountTimesI + " 人次； " + strInsertWeekCountPeopleI + "人數。\n";
				messageText += "本週平均：" + avgInsertWeekCountTimesI + " 人次； " + avgInsertWeekCountPeopleI + "% 使用人數比例。\n";
				messageText += "本月總計：" + strInsertMonthCountTimesI + " 人次； " + strInsertMonthCountPeopleI + "人數。\n";
				messageText += "本月平均：" + avgInsertMonthCountTimesI + " 人次； " + avgInsertMonthCountPeopleI + "% 使用人數比例。\n\n";
	        
				messageText += "--國泰人壽業務--列印建議書：\n";
				messageText += "本日：" + strPrintCountTimes + " 人次； " + strPrintCountPeople + "人數。\n";
				messageText += "本週總計：" + strPrintWeekCountTimes + " 人次； " + strPrintWeekCountPeople + "人數。\n";
				messageText += "本週平均：" + avgPrintWeekCountTimes + " 人次； " + avgPrintWeekCountPeople + "% 使用人數比例。\n";
				messageText += "本月總計：" + strPrintMonthCountTimes + " 人次； " + strPrintMonthCountPeople + "人數。\n";
				messageText += "本月平均：" + avgPrintMonthCountTimes + " 人次； " + avgPrintMonthCountPeople + "% 使用人數比例。\n\n";
				messageText += "註：使用人數比例︰當週或當月使用人數總計 / 專招加展業人數\n";
				messageText += "　　專招加展業人數︰"+EmployeeCount+"\n";
				/*
        messageText += "--國泰人壽業務--\n";
        messageText += "建議書使用率：" + ((strHomePageCountTimes != null) ? strHomePageCountTimes : "0") + " 人次； " + strHomePageCountPeople + "人數。\n";
        messageText += "編輯傳統型：" + ((strInsertCountTimesT != null) ? strInsertCountTimesT : "0") + " 人次； " + strInsertCountPeopleT + "人數。\n";
        messageText += "編輯投資型：" + ((strInsertCountTimesI != null) ? strInsertCountTimesI : "0") + " 人次； " + strInsertCountPeopleI + "人數。\n";
        messageText += "列印建議書：" + strPrintCountTimes + " 人次； " + strPrintCountPeople + "人數。\n\n";
        
        messageText += "--國泰人壽業務 本週總計--\n";
        messageText += "建議書使用率：" + ((strHomePageWeekCountTimes != null) ? strHomePageWeekCountTimes : "0") + " 人次； " + strHomePageWeekCountPeople + "人數。\n";
        messageText += "編輯傳統型：" + ((strInsertWeekCountTimesT != null) ? strInsertWeekCountTimesT : "0") + " 人次； " + strInsertWeekCountPeopleT + "人數。\n";
        messageText += "編輯投資型：" + ((strInsertWeekCountTimesI != null) ? strInsertWeekCountTimesI : "0") + " 人次； " + strInsertWeekCountPeopleI + "人數。\n";
        messageText += "列印建議書：" + strPrintWeekCountTimes + " 人次； " + strPrintWeekCountPeople + "人數。\n\n";
        
        messageText += "--國泰人壽業務 本週平均--\n";
        messageText += "建議書使用率：" + ((strHomePageWeekCountTimes != null) ? String.valueOf(round(Double.parseDouble(strHomePageWeekCountTimes)/5)) : "0") + " 人次； " + String.valueOf(round(Double.parseDouble(strHomePageWeekCountPeople)/5)) + "人數。\n";
        messageText += "編輯傳統型：" + ((strInsertWeekCountTimesT != null) ? String.valueOf(round(Double.parseDouble(strInsertWeekCountTimesT)/5)) : "0") + " 人次； " + String.valueOf(round(Double.parseDouble(strInsertWeekCountPeopleT)/5)) + "人數。\n";
        messageText += "編輯投資型：" + ((strInsertWeekCountTimesI != null) ? String.valueOf(round(Double.parseDouble(strInsertWeekCountTimesI)/5)) : "0") + " 人次； " + String.valueOf(round(Double.parseDouble(strInsertWeekCountPeopleI)/5)) + "人數。\n";
        messageText += "列印建議書：" + String.valueOf(round(Double.parseDouble(strPrintWeekCountTimes)/5)) + " 人次； " + String.valueOf(round(Double.parseDouble(strPrintWeekCountPeople)/5)) + "人數。\n\n";
        
        messageText += "--國泰人壽業務 本月總計--\n";
        messageText += "建議書使用率：" + ((strHomePageMonthCountTimes != null) ? strHomePageMonthCountTimes : "0") + " 人次； " + strHomePageMonthCountPeople + "人數。\n";
        messageText += "編輯傳統型：" + ((strInsertMonthCountTimesT != null) ? strInsertMonthCountTimesT : "0") + " 人次； " + strInsertMonthCountPeopleT + "人數。\n";
        messageText += "編輯投資型：" + ((strInsertMonthCountTimesI != null) ? strInsertMonthCountTimesI : "0") + " 人次； " + strInsertMonthCountPeopleI + "人數。\n";
        messageText += "列印建議書：" + strPrintMonthCountTimes + " 人次； " + strPrintMonthCountPeople + "人數。\n\n";
        
        messageText += "--國泰人壽業務 本月平均--\n";
        messageText += "建議書使用率：" + ((strHomePageMonthCountTimes != null) ? String.valueOf(round(Double.parseDouble(strHomePageMonthCountTimes)/22)) : "0") + " 人次； " + String.valueOf(round(Double.parseDouble(strHomePageMonthCountPeople)/22)) + "人數。\n";
        messageText += "編輯傳統型：" + ((strInsertMonthCountTimesT != null) ? String.valueOf(round(Double.parseDouble(strInsertMonthCountTimesT)/22)) : "0") + " 人次； " + String.valueOf(round(Double.parseDouble(strInsertMonthCountPeopleT)/22)) + "人數。\n";
        messageText += "編輯投資型：" + ((strInsertMonthCountTimesI != null) ? String.valueOf(round(Double.parseDouble(strInsertMonthCountTimesI)/22)) : "0") + " 人次； " + String.valueOf(round(Double.parseDouble(strInsertMonthCountPeopleI)/22)) + "人數。\n";
        messageText += "列印建議書：" + String.valueOf(round(Double.parseDouble(strPrintMonthCountTimes)/22)) + " 人次； " + String.valueOf(round(Double.parseDouble(strPrintMonthCountPeople)/22)) + "人數。\n\n";
				*/
        /*
        messageText += "--國泰世華理專--\n";
        messageText += "建議書使用率：" + ((strHomePageCountTimesBank != null) ? strHomePageCountTimesBank : "0") + " 人次； " + strHomePageCountPeopleBank + "人數。";
        if (strHomePageCountName.equals("")) {
            messageText += "〈今日無人使用〉\n";
        }
        else {
            messageText += "〈";
            messageText += strHomePageCountName;
            messageText += " 〉\n";
        }
        
        messageText += "編輯傳統型：" + ((strInsertCountTimesTBank != null) ? strInsertCountTimesTBank : "0") + " 人次； " + strInsertCountPeopleTBank + "人數。";
        if (strInsertCountNameT.equals("")) {
            messageText += "〈今日無人使用〉\n";
        }
        else {
            messageText += "〈";
            messageText += strInsertCountNameT;
            messageText += " 〉\n";
        }
        
        messageText += "編輯投資型：" + ((strInsertCountTimesIBank != null) ? strInsertCountTimesIBank : "0") + " 人次； " + strInsertCountPeopleIBank + "人數。";
        if (strInsertCountNameI.equals("")) {
            messageText += "〈今日無人使用〉\n";
        }
        else {
            messageText += "〈";
            messageText += strInsertCountNameI;
            messageText += " 〉\n";
        }

        messageText += "列印建議書：" + strPrintCountTimesBank + " 人次； " + strPrintCountPeopleBank + "人數。";
        if (strPrintCountName.equals("")) {
            messageText += "〈今日無人使用〉\n\n";
        }
        else {
            messageText += "〈";
            messageText += strPrintCountName;
            messageText += " 〉\n\n";
        }
        */
        //---<---content------
        if (strContent != null && !strContent.equals("")) {
            messageText += strContent;
        }
        //------content--->---

        boolean sessionDebug = false;
        InternetAddress[] address = null;
        //InternetAddress[] addresses_cc = null;
        //InternetAddress[] addresses_bcc = null;


        try {
            // set properties...
            Properties props = System.getProperties();
	        props.put("mail.host", mailserver);
	        props.put("mail.transport.protocol", "smtp");
	        props.put("mail.smtp.auth", "true");
            // new Session service...
	    //javax.mail.Session mailSession = javax.mail.Session.getDefaultInstance(props,null);
	    //javax.mail.Session mailSession = javax.mail.Session.getDefaultInstance(props, new SMTPAuthenticator(From, "crm"));
	    javax.mail.Session mailSession = javax.mail.Session.getDefaultInstance(props, new SMTPAuthenticator(From, "newcrm"));
            mailSession.setDebug(sessionDebug);
	
            // new Message...
            //Message msg = new MimeMessage(mailSession);
            MimeMessage msg = new MimeMessage(mailSession);		//mail server換過，主旨部份只接受有編碼的主旨 20090210
            // set sender...
            msg.setFrom(new InternetAddress(From,"建議書使用率"));
  
            // set recipient...
            address = InternetAddress.parse(to,false);
            msg.setRecipients(Message.RecipientType.TO, address);
            //address_cc = InternetAddress.parse(cc,false);
            //msg.setRecipients(Message.RecipientType.CC , addresses_cc);
            //address_bcc = InternetAddress.parse(bcc,false);
            //msg.setRecipients(Message.RecipientType.BCC, addresses_bcc);
  
            // set Subject...
            //msg.setSubject(Subject);
			msg.setSubject(Subject,"big5");	//mail server換過，主旨部份只接受有編碼的主旨 20090210

            // set SendDate...
            msg.setSentDate(new java.util.Date());
 
            // set Content...
            msg.setText(messageText);
            // msg.setContent(messageText, "text/html; charset=Big5");      // 以 HTML 發送
  
            // send Message...
            Transport.send(msg);
        }
        catch (Exception e) {
            //---<---log------
            writeLog(strDateOutput + ": email's exception: " + e.toString());
            //------log--->---
            blnIsException = true;
        }

        //---<---log------
        if (blnIsException == true) {
            writeLog(strDateOutput + ": exception...");
        }
        else {
            writeLog(strDateOutput + ": successful...");
        }

        //------log--->---
    
    }

    private static void writeLog(String str) {
        try {
            FileWriter fw = new FileWriter("SendCounter.log", true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(str);
            bw.newLine();
            bw.close();
            fw.close();
        }
        catch (Exception e) {
            System.out.println(e.toString());
        }
    }
    
    private static double round(double in){	//小數點以下兩位四捨五入 popdog 2010/03/30
    	return Math.round(in*100)/100.0;
    }
    
    private static double round( double in , int scale ){//四捨五入，可自訂小數位，目前未用，也許未來可能會用到，順手寫寫 popdog 2010/03/30
    	if(scale<0)
    		scale=0;
    	double div = 1.0;
    	for( int i=0;i<scale;i++ ){
    		div *= 10 ;
    	}
    	return Math.round(in*div)/div ;
    }
}

import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
import java.util.*;
import java.io.*;
import java.net.*;
import java.sql.*;

class SendCounter {
	public static void main(String[] args) {
		boolean blnIsException = false;

		//---<---日期------
		Calendar today = Calendar.getInstance();
		long lngYesterday = today.getTimeInMillis()-86400000;
		today.setTimeInMillis(lngYesterday);
		int intyear = today.get(Calendar.YEAR);
		int intmonth = today.get(Calendar.MONTH)+1;
		int intday = today.get(Calendar.DATE);
		String strDate = String.valueOf(intyear)+String.valueOf(intmonth)+String.valueOf(intday);
		String strDateOutput = String.valueOf(intyear) + " / " + String.valueOf(intmonth) + " / " + String.valueOf(intday);
		String strDateOutput2 = String.valueOf(intyear) + " / " + String.valueOf(intmonth);
		
		//執行SQL語法、取得統計資料、最後塞進信件本文
		
        String strTotalCount = "";	//國泰世華理專總人數
        
		String Login_Times = "";	//使用者登入人次
        String Login_People = "";	//使用者登入人數
        
		String SACM_Times = "";		//資料蒐集輸入使用人次
		String SACM_People = "";	//資料蒐集輸入使用人數
		
		String SAPC_Times = "";		//保單校正使用人次
		String SAPC_People = "";	//保單校正使用人數
		
		String SAPP_Times = "";		//商品建議書使用人次
		String SAPP_People = "";	//商品建議書使用人數
		
        String SAPD_Times = "";		//桌機版新契約投保系統使用人次
        String SAPD_People = "";	//桌機版新契約投保系統使用人數
        
		String SENC_Times = "";		//新契約服務使用人次
		String SENC_People = "";	//新契約服務使用人數
		
		String SEAS_Times = "";		//理賠使用人次
		String SEAS_People = "";	//理賠使用人數
		
		String SEBF_Times = "";		//保費使用人次
		String SEBF_People = "";	//保費使用人數
		
		String SEBI_Times = "";		//保全使用人次
		String SEBI_People = "";	//保全使用人數
		
        String SEDL_Times = "";		//文件下載使用人次
        String SEDL_People = "";	//文件下載使用人數
		
        String SEQD_Times = "";		//投資型商品專區使用人次
        String SEQD_People = "";	//投資型商品專區使用人數
		
		Vector vtFirstTen = new Vector();//使用率排名前十名
		
        String strTodayCustomer = "" ;  //當日新增客戶數
        String strTotalCustomer = "" ;  //累績客戶數
		
		ArrayList mailList = new ArrayList();//20100310修改由資料庫抓出收件者資料
		
        //---<---get db data------
		try {
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
		}
		catch (Exception e1) {
			System.out.println(e1.toString());
			blnIsException = true;
		}

		Connection objConn = null;

		try {
			//20100310修改要記得改回來
            objConn = DriverManager.getConnection("jdbc:odbc:BankCRM");
			Calendar objCal1 = Calendar.getInstance();
			Calendar objCal2 = Calendar.getInstance();
			objCal1.add(Calendar.DATE, -1);
            //統計日0時0點0分
			String strPrintBeginTime = objCal1.get(Calendar.YEAR) + "-" + (objCal1.get(Calendar.MONTH) + 1) + "-" + objCal1.get(Calendar.DATE) + " 00:00:00";
            //統計日後一日0時0點0分
			String strPrintEndTime = objCal2.get(Calendar.YEAR) + "-" + (objCal2.get(Calendar.MONTH) + 1) + "-" + objCal2.get(Calendar.DATE) + " 00:00:00";
	        //統計日當月1日
			String strBeginTime = objCal1.get(Calendar.YEAR) + "-" + (objCal1.get(Calendar.MONTH) + 1) + "-1 00:00:00";			
			//統計日
			String strBeginDate = objCal1.get(Calendar.YEAR) + "-" + (objCal1.get(Calendar.MONTH) + 1) + "-" + objCal1.get(Calendar.DATE) ;

            //計算總人數、總人次
            PreparedStatement objPStmt = objConn.prepareStatement(" SELECT count(a.USER_ID),count(distinct a.USER_ID) FROM BANKUSER.USER_ACT_LOG a LEFT JOIN BANKUSER.UWCEMP b on a.USER_ID = b.AGID WHERE b.BT_NOTE in ('FA1','FA2','FB1','FB2','PFB') AND b.STATUS = 1 AND a.ACT_TIME BETWEEN '" + strPrintBeginTime + "' and '" + strPrintEndTime + "' AND a.FUNC_ID = 'LOGIN' ");

			ResultSet objRS = objPStmt.executeQuery();

			while (objRS.next()) {
				Login_Times = objRS.getString(1);
				Login_People = objRS.getString(2);
			}
			
			//國泰世華理專總人數
			objPStmt = objConn.prepareStatement(" SELECT count(AGID) FROM BANKUSER.UWCEMP WHERE BT_NOTE in ('FA1','FA2','FB1','FB2','PFB') and STATUS = 1 ");
			objRS = objPStmt.executeQuery();
			while (objRS.next()) {
				strTotalCount = objRS.getString(1);
			}
			
			//資料蒐集輸入
			objPStmt = objConn.prepareStatement(" SELECT count(a.USER_ID),count(distinct a.USER_ID) FROM BANKUSER.USER_ACT_LOG a LEFT JOIN BANKUSER.UWCEMP b on a.USER_ID = b.AGID WHERE b.BT_NOTE in ('FA1','FA2','FB1','FB2','PFB') AND b.STATUS = 1 AND a.ACT_TIME BETWEEN '" + strPrintBeginTime + "' and '" + strPrintEndTime + "' AND a.FUNC_ID = 'SACM0100' ");
			objRS = objPStmt.executeQuery();
			while (objRS.next()) {
				SACM_Times = objRS.getString(1);
				SACM_People = objRS.getString(2);
			}
			
			//保單校正
			objPStmt = objConn.prepareStatement(" SELECT count(a.USER_ID),count(distinct a.USER_ID) FROM BANKUSER.USER_ACT_LOG a LEFT JOIN BANKUSER.UWCEMP b on a.USER_ID = b.AGID WHERE b.BT_NOTE in ('FA1','FA2','FB1','FB2','PFB') AND b.STATUS = 1 AND a.ACT_TIME BETWEEN '" + strPrintBeginTime + "' and '" + strPrintEndTime + "' AND a.FUNC_ID = 'SAPC0100' ");
			objRS = objPStmt.executeQuery();
			while (objRS.next()) {
				SAPC_Times = objRS.getString(1);
				SAPC_People = objRS.getString(2);
			}
			
			//商品建議書
			objPStmt = objConn.prepareStatement(" SELECT count(a.USER_ID),count(distinct a.USER_ID) FROM BANKUSER.USER_ACT_LOG a LEFT JOIN BANKUSER.UWCEMP b on a.USER_ID = b.AGID WHERE b.BT_NOTE in ('FA1','FA2','FB1','FB2','PFB') AND b.STATUS = 1 AND a.ACT_TIME BETWEEN '" + strPrintBeginTime + "' and '" + strPrintEndTime + "' AND a.FUNC_ID = 'SAPP0100' ");
			objRS = objPStmt.executeQuery();
			while (objRS.next()) {
				SAPP_Times = objRS.getString(1);
				SAPP_People = objRS.getString(2);
			}
			
			//桌機版新契約投保系統
			objPStmt = objConn.prepareStatement(" SELECT count(a.USER_ID),count(distinct a.USER_ID) FROM BANKUSER.USER_ACT_LOG a LEFT JOIN BANKUSER.UWCEMP b on a.USER_ID = b.AGID WHERE b.BT_NOTE in ('FA1','FA2','FB1','FB2','PFB') AND b.STATUS = 1 AND a.ACT_TIME BETWEEN '" + strPrintBeginTime + "' and '" + strPrintEndTime + "' AND a.FUNC_ID = 'SAPD0100' ");
			objRS = objPStmt.executeQuery();
			while (objRS.next()) {
				SAPD_Times = objRS.getString(1);
				SAPD_People = objRS.getString(2);
			}
			
			//新契約服務
			objPStmt = objConn.prepareStatement(" SELECT count(a.USER_ID),count(distinct a.USER_ID) FROM BANKUSER.USER_ACT_LOG a LEFT JOIN BANKUSER.UWCEMP b on a.USER_ID = b.AGID WHERE b.BT_NOTE in ('FA1','FA2','FB1','FB2','PFB') AND b.STATUS = 1 AND a.ACT_TIME BETWEEN '" + strPrintBeginTime + "' and '" + strPrintEndTime + "' AND (a.FUNC_ID like 'SENC%' OR a.FUNC_ID in ('SEBI1200','SEBI0100','SEBI0200','SEBI0300')) ");
			objRS = objPStmt.executeQuery();
			while (objRS.next()) {
				SENC_Times = objRS.getString(1);
				SENC_People = objRS.getString(2);
			}
			
			//理賠
			objPStmt = objConn.prepareStatement(" SELECT count(a.USER_ID),count(distinct a.USER_ID) FROM BANKUSER.USER_ACT_LOG a LEFT JOIN BANKUSER.UWCEMP b on a.USER_ID = b.AGID WHERE b.BT_NOTE in ('FA1','FA2','FB1','FB2','PFB') AND b.STATUS = 1 AND a.ACT_TIME BETWEEN '" + strPrintBeginTime + "' and '" + strPrintEndTime + "' AND (a.FUNC_ID like 'SEAS%' OR a.FUNC_ID = 'SEEO0100') ");
			objRS = objPStmt.executeQuery();
			while (objRS.next()) {
				SEAS_Times = objRS.getString(1);
				SEAS_People = objRS.getString(2);
			}
			
			//保費
			objPStmt = objConn.prepareStatement(" SELECT count(a.USER_ID),count(distinct a.USER_ID) FROM BANKUSER.USER_ACT_LOG a LEFT JOIN BANKUSER.UWCEMP b on a.USER_ID = b.AGID WHERE b.BT_NOTE in ('FA1','FA2','FB1','FB2','PFB') AND b.STATUS = 1 AND a.ACT_TIME BETWEEN '" + strPrintBeginTime + "' and '" + strPrintEndTime + "' AND (a.FUNC_ID like 'SEBF%' OR a.FUNC_ID in ('SEBI0400','SEBI0500','SEBI0800','SEBI1000','ADCQ0600')) ");
			objRS = objPStmt.executeQuery();
			while (objRS.next()) {
				SEBF_Times = objRS.getString(1);
				SEBF_People = objRS.getString(2);
			}
			
			//保全
			objPStmt = objConn.prepareStatement(" SELECT count(a.USER_ID),count(distinct a.USER_ID) FROM BANKUSER.USER_ACT_LOG a LEFT JOIN BANKUSER.UWCEMP b on a.USER_ID = b.AGID WHERE b.BT_NOTE in ('FA1','FA2','FB1','FB2','PFB') AND b.STATUS = 1 AND a.ACT_TIME BETWEEN '" + strPrintBeginTime + "' and '" + strPrintEndTime + "' AND a.FUNC_ID in ('SEBI0600','SEBI0700','SEBI0900','SEBI1100') ");
			objRS = objPStmt.executeQuery();
			while (objRS.next()) {
				SEBI_Times = objRS.getString(1);
				SEBI_People = objRS.getString(2);
			}
			
			//文件下載
			objPStmt = objConn.prepareStatement(" SELECT count(a.USER_ID),count(distinct a.USER_ID) FROM BANKUSER.USER_ACT_LOG a LEFT JOIN BANKUSER.UWCEMP b on a.USER_ID = b.AGID WHERE b.BT_NOTE in ('FA1','FA2','FB1','FB2','PFB') AND b.STATUS = 1 AND a.ACT_TIME BETWEEN '" + strPrintBeginTime + "' and '" + strPrintEndTime + "' AND a.FUNC_ID like 'SEDL%' ");
			objRS = objPStmt.executeQuery();
			while (objRS.next()) {
				SEDL_Times = objRS.getString(1);
				SEDL_People = objRS.getString(2);
			}
			
			//投資型商品專區
			objPStmt = objConn.prepareStatement(" SELECT count(a.USER_ID),count(distinct a.USER_ID) FROM BANKUSER.USER_ACT_LOG a LEFT JOIN BANKUSER.UWCEMP b on a.USER_ID = b.AGID WHERE b.BT_NOTE in ('FA1','FA2','FB1','FB2','PFB') AND b.STATUS = 1 AND a.ACT_TIME BETWEEN '" + strPrintBeginTime + "' and '" + strPrintEndTime + "' AND a.FUNC_ID like 'SEQD%' ");
			objRS = objPStmt.executeQuery();
			while (objRS.next()) {
				SEQD_Times = objRS.getString(1);
				SEQD_People = objRS.getString(2);
			}
			
			//當日新增的客戶
			objPStmt = objConn.prepareStatement(" SELECT count(a.CSIDNO) FROM BANKUSER.O_CUSTOMER a LEFT JOIN BANKUSER.UWCEMP b on a.AGIDNO = b.AGID WHERE b.BT_NOTE in ('FA1','FA2','FB1','FB2','PFB') AND b.STATUS = 1 AND a.INDATE = '"+ strBeginDate +"' ");
			objRS = objPStmt.executeQuery();
			while (objRS.next()) {
				strTodayCustomer = objRS.getString(1);
			}
			
            //累積新增的客戶
			objPStmt = objConn.prepareStatement(" SELECT count(a.CSIDNO) FROM BANKUSER.O_CUSTOMER a LEFT JOIN BANKUSER.UWCEMP b on a.AGIDNO = b.AGID WHERE b.BT_NOTE in ('FA1','FA2','FB1','FB2','PFB') AND b.STATUS = 1 AND a.AGIDNO NOT IN (SELECT USER_ID FROM BANKUSER.USER WHERE CEBANKNO = 'CXL90' ) ");
			objRS = objPStmt.executeQuery();
			while (objRS.next()) {
				strTotalCustomer = objRS.getString(1);
			}
			
			//當月使用次數前十名
			String strSQLFirst = " select z.FUNC_ID,y.FUNC_NAME,x.FUNC_NAME,count(*) "
            + " from "
            + " ( "
            + " select a.USER_ID,substr(a.FUNC_ID,1,4) || '0000' LEVEL2_ID,a.FUNC_ID "
            + " from BANKUSER.USER_ACT_LOG a "
            + " left join BANKUSER.UWCEMP b on a.USER_ID = b.AGID "
            + " where b.BT_NOTE in ('FA1','FA2','FB1','FB2','PFB') and b.STATUS = 1 "
            + " and a.ACT_TIME > '" + strBeginTime + "'"
            + " and a.FUNC_ID not in ('LOGIN','ILHP0100') "
            + " ) z ,BANKUSER.FUNCTION x,BANKUSER.FUNCTION y "
            + " where z.FUNC_ID = x.FUNC_ID and z.LEVEL2_ID = y.FUNC_ID "
            + " group by z.FUNC_ID,y.FUNC_NAME,x.FUNC_NAME "
            + " order by count(*) desc fetch first 10 rows only";
			
            objPStmt = objConn.prepareStatement(strSQLFirst);
            objRS = objPStmt.executeQuery();
            while (objRS.next()) {
                vtFirstTen.add(objRS.getString(3)+"<td align='center' class='ad'>"+objRS.getString(4));
            }
            
            objPStmt = objConn.prepareStatement("select RC_NAME,RC_MAIL from INSDB.DAILYSTATS_RECEIVER where dailyx = '銀保專區'");
            objRS = objPStmt.executeQuery();
            while (objRS.next()) {
				String[] address = {objRS.getString(1),objRS.getString(2)};
				mailList.add(address);
            }
            
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

		String mailserver   = "sendmail.cathaylife.com.tw";
		String From         = "cathaylife_banc@cathlife.com.tw";
		String Subject      = "國泰人壽銀保專區使用狀況";

		StringBuffer sb = new StringBuffer();

	try {
		
		sb.append("<HTML><HEAD><meta http-equiv='Content-Type' content='text/html; charset=BIG5'><title>國泰人壽 銀保專區</title>");
		sb.append("<style type='text/css'>");
		sb.append("	.sa { background-color:#FFe8e8} .ad { background-color:#ffefdf} .se { background-color:#e8f3ff} ");
		sb.append("</style>");
		sb.append("</head>");
		sb.append("<body bgcolor='FFFFFF'><center>");
		sb.append("<table bgcolor='FFFFFF' width='70%' border='1'>");
		
		sb.append("<tr bgcolor='E9F7F0'>");
		sb.append("<td colspan='4' align='center'>《 " + strDateOutput + " 國泰人壽銀保專區 使用狀況 ( 統計時間：日 ) 》");
		
		sb.append("<tr valign='middle'>");
		sb.append("<td colspan='2' width='40%' align='center'>\\</td>");
		sb.append("<td width='30%' align='center'>理專總人數<br>( " + strTotalCount + " 人 )");
		sb.append("<td width='30%' align='center'>總人次");
		
		sb.append("<tr>");
		sb.append("<td colspan='2' align='center'>國泰人壽銀保專區使用量");
		sb.append("<td align='center'>" + Login_People + " 人 ");
		sb.append("<td align='center'>" + Login_Times + " 人 ");
		
		//*********** 行銷支援 *****************************
		sb.append("<tr>");
		sb.append("<td align='center' rowspan='4' width='12%' class='sa'>行銷支援");
		sb.append("<td class='sa'>資料蒐集輸入");
		sb.append("<td align='center' class='sa'>" + SACM_People);
		sb.append("<td align='center' class='sa'>" + SACM_Times);
		
		sb.append("<tr>");
		sb.append("<td class='sa'>保單校正");
		sb.append("<td align='center' class='sa'>" + SAPC_People);
		sb.append("<td align='center' class='sa'>" + SAPC_Times);
		
		sb.append("<tr>");
		sb.append("<td class='sa'>商品建議書");
		sb.append("<td align='center' class='sa'>" + SAPP_People);
		sb.append("<td align='center' class='sa'>" + SAPP_Times);
		
		sb.append("<tr>");
		sb.append("<td class='sa'>桌機版新契約投保系統");
		sb.append("<td align='center' class='sa'>" + SAPD_People);
		sb.append("<td align='center' class='sa'>" + SAPD_Times);
		
		//*********** 服務支援 *****************************
		sb.append("<tr>");
		sb.append("<td align='center' rowspan='6' width='12%' class='se'>服務支援");
		sb.append("<td class='se'>新契約服務");
		sb.append("<td align='center' class='se'>" + SENC_People);
		sb.append("<td align='center' class='se'>" + SENC_Times);

		sb.append("<tr>");
		sb.append("<td class='se'>理賠");
		sb.append("<td align='center' class='se'>" + SEAS_People);
		sb.append("<td align='center' class='se'>" + SEAS_Times);

		sb.append("<tr>");
		sb.append("<td class='se'>保費");
		sb.append("<td align='center' class='se'>" + SEBF_People);
		sb.append("<td align='center' class='se'>" + SEBF_Times);

		sb.append("<tr>");
		sb.append("<td class='se'>保全");
		sb.append("<td align='center' class='se'>" + SEBI_People);
		sb.append("<td align='center' class='se'>" + SEBI_Times);

		sb.append("<tr>");
		sb.append("<td class='se'>文件下載");
		sb.append("<td align='center' class='se'>" + SEDL_People);
		sb.append("<td align='center' class='se'>" + SEDL_Times);
		
		sb.append("<tr>");
		sb.append("<td class='se'>投資型商品專區");
		sb.append("<td align='center' class='se'>" + SEQD_People);
		sb.append("<td align='center' class='se'>" + SEQD_Times);
		
		//***********新增客戶資料*****************************
		sb.append("<tr>");
		sb.append("<td rowspan='3' colspan='2' align='center'>新增客戶資料");
		sb.append("<td align='center'>當日新增 ");
		sb.append("<td align='center'>累積新增 ");
		
		sb.append("<tr>");
		sb.append("<td align='center'>" + strTodayCustomer + " 人 ");
		sb.append("<td align='center'>" + strTotalCustomer + " 人 ");
		sb.append("</table>");

        if (vtFirstTen.size()>0) {
        	
            sb.append("	<p><p><hr><p><p> ");
            
            //*********** 當月使用次數前十名 *****************************
    		sb.append("<table bgcolor='FFFFFF' width='70%' border='1'>");
    		sb.append("<tr bgcolor='E9F7F0'>");
    		sb.append("<td colspan='2' align='center'>《 " + strDateOutput2 + " 國泰人壽銀保專區 使用功能排行榜 ( 統計時間：月 ) 》");
    		sb.append("<tr>");
    		sb.append("<td colspan='2' align='center'>《 當月使用次數前十名 》");
    		for(int i=0 ; i<10 ; i++) {
                sb.append("<tr>");
                if(vtFirstTen.size()<=i) {
                    sb.append("<td width='50%' align='center' class='ad'>");
                } else {
                    sb.append("<td width='50%' align='center' class='ad'>" + vtFirstTen.get(i));
                }
            }
        }
        sb.append("</table></center></body></html>");
        
        String messageText = sb.toString();
        
		boolean sessionDebug = false;
		InternetAddress[] toAddress = new InternetAddress[mailList.size()];

		for(int i=0;i<mailList.size();i++){
			String[] address =(String[])mailList.get(i);
			toAddress[i]=new InternetAddress(address[1].toLowerCase(),address[0],"big5");
		}
		
			// set properties...
			Properties props = System.getProperties();
			props.put("mail.host", mailserver);
			props.put("mail.transport.protocol", "smtp");
			props.put("mail.smtp.auth", "true");

			// new Session service...
			javax.mail.Session mailSession = javax.mail.Session.getDefaultInstance(props, new SMTPAuthenticator(From, "crmbank2"));

			mailSession.setDebug(sessionDebug);
			
			
			// new Message...
			MimeMessage msg = new MimeMessage(mailSession);

			// set sender...
			msg.setFrom(new InternetAddress(From,"國泰人壽 銀保專區","big5"));

			// set recipient...
			//address = InternetAddress.parse(to,false);


			msg.setRecipients(Message.RecipientType.TO, toAddress);

			// set Subject...
			msg.setSubject(Subject,"big5");

			// set SendDate...
			msg.setSentDate(new java.util.Date());

			// set Content...
			// msg.setText(messageText);
			 msg.setContent(messageText, "text/html; charset=Big5");      // 以 HTML 發送

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
}

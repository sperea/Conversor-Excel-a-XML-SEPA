package sepa;

import java.util.ArrayList;
import java.util.Iterator;
import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class XmlSEPATransfersFile {

	

	private GroupHeader groupHeader;
	
	private TransferPaymentInformation payment;
	
	
	/* información del pago 1..N */
	

	private ArrayList<TransferPaymentInformation> infopagos = new ArrayList<TransferPaymentInformation>();
	
	public void InsertarPago(TransferPaymentInformation pago)
	{
		this.infopagos.add(pago);
	}
	
	public GroupHeader getGroupHeader() {
		return groupHeader;
	}

	public void setGroupHeader(GroupHeader groupHeader) {
		this.groupHeader = groupHeader;
	}

	public TransferPaymentInformation getPayment() {
		return payment;
	}

	public void setPayment(TransferPaymentInformation payment) {
		this.payment = payment;
	}
	
	public void GenerateXML(String filePath)
	{
	      try {
	    	  
	          DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
	          DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

	          // root elements
	          Document doc = docBuilder.newDocument();
	          Element rootElement = doc.createElement("Document");
	          // xmlns="urn:iso:std:iso:20022:tech:xsd:pain.001.001.03" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	          Attr attr = doc.createAttribute("xmlns");
              attr.setValue("urn:iso:std:iso:20022:tech:xsd:pain.001.001.03");
              rootElement.setAttributeNode(attr);
	          attr = doc.createAttribute("xmlns:xsi");
              attr.setValue("http://www.w3.org/2001/XMLSchema-instance");
              rootElement.setAttributeNode(attr);
	          doc.appendChild(rootElement);


	          // CstmrCdtTrfInitn
	          Element CstmrCdtTrfInitn = doc.createElement("CstmrCdtTrfInitn");
	          rootElement.appendChild(CstmrCdtTrfInitn);

	          /*********************************************
	           *  HEADER
	           ********************************************/
	          
	          // CstmrCdtTrfInitn
	          Element GrpHdr = doc.createElement("GrpHdr");
	          CstmrCdtTrfInitn.appendChild(GrpHdr);
	          
	          Element MsgId = doc.createElement("MsgId");
	          MsgId.setTextContent(this.groupHeader.getMsgId());
	          GrpHdr.appendChild(MsgId);
	          
	          Element NbOfTxs_header = doc.createElement("NbOfTxs");
	          NbOfTxs_header.setTextContent(this.groupHeader.getNbOfTxs());
	          GrpHdr.appendChild(NbOfTxs_header);
	          
	          Element CtrlSum_header = doc.createElement("CtrlSum");
	          CtrlSum_header.setTextContent(this.groupHeader.getCtrlSum());
	          GrpHdr.appendChild(CtrlSum_header);
	          
	          // InitgPty
	          Element InitgPty = doc.createElement("InitgPty");
	          GrpHdr.appendChild(InitgPty);
	          
	          Element Nm_header = doc.createElement("Nm");
	          Nm_header.setTextContent(this.groupHeader.getNm());
	          InitgPty.appendChild(Nm_header);
	          
	          // + Id
	          Element Id_header = doc.createElement("Id");
	          InitgPty.appendChild(Id_header);
	          
	          // ++ OrgId
	          Element OrgId_header = doc.createElement("OrgId");
	          Id_header.appendChild(OrgId_header);
	          
	          // +++ Othr
	          Element Othr_header = doc.createElement("Othr");
	          OrgId_header.appendChild(Othr_header);
	          
	          Element Id_value_header = doc.createElement("Id_value_header");
	          Id_value_header.setTextContent(this.groupHeader.getId());
	          Othr_header.appendChild(CtrlSum_header);
	          
	          /*******************************************
	           *  INFORMACION DEL PAGO
	           *******************************************/
	          
	          Iterator<TransferPaymentInformation> it = infopagos.iterator();
	          
	          while (it.hasNext()) {
	        	 TransferPaymentInformation auxInfoPago = it.next();
	        	 
	        	 Element PmtInf = doc.createElement("PmtInf");
		         CstmrCdtTrfInitn.appendChild(PmtInf);
		         
		         // PmtInfId
		         Element PmtInfId = doc.createElement("PmtInfId");
		         PmtInfId.setTextContent(auxInfoPago.getPmtInfId());
		         PmtInf.appendChild(PmtInfId);
		         
		         // PmtMtd
		         Element PmtMtd = doc.createElement("PmtMtd");
		         PmtMtd.setTextContent(auxInfoPago.getPmtMtd());
		         PmtInf.appendChild(PmtMtd);
		         
		         // BtchBookg
		         Element BtchBookg = doc.createElement("BtchBookg");
		         BtchBookg.setTextContent(auxInfoPago.getBtchBookg());
		         PmtInf.appendChild(BtchBookg);
		         
		         // NbOfTxs
		         Element NbOfTxs = doc.createElement("NbOfTxs");
		         NbOfTxs.setTextContent(auxInfoPago.getNbOfTxs());
		         PmtInf.appendChild(NbOfTxs);
		         
		         // CtrlSum
		         Element CtrlSum = doc.createElement("CtrlSum");
		         CtrlSum.setTextContent(auxInfoPago.getCtrlSum());
		         PmtInf.appendChild(CtrlSum);
		         
		         // PmtTpInf
		         Element PmtTpInf = doc.createElement("PmtTpInf");
		         PmtInf.appendChild(PmtTpInf);
		         
		         // + InstrPrty
		         Element InstrPrty = doc.createElement("InstrPrty");
		         InstrPrty.setTextContent(auxInfoPago.getInstrPrty());
		         PmtTpInf.appendChild(InstrPrty);
		         
		         // + SvcLvl
		         Element SvcLvl = doc.createElement("SvcLvl");
		         PmtTpInf.appendChild(SvcLvl);
		         
		         // ++ CD
		         Element CD = doc.createElement("CD");
		         CD.setTextContent(auxInfoPago.getCd());
		         SvcLvl.appendChild(CD);
		         
		         // + LclInstrm
		         Element LclInstrm = doc.createElement("LclInstrm");
		         PmtTpInf.appendChild(LclInstrm);
		         
		         // ++ CD
		         Element Cd_LclInstrm = doc.createElement("CD");
		         Cd_LclInstrm.setTextContent(auxInfoPago.getCd_LclInstrm());
		         LclInstrm.appendChild(Cd_LclInstrm);
		         
		         // + CtgyPurp
		         Element CtgyPurp = doc.createElement("CtgyPurp");
		         PmtTpInf.appendChild(CtgyPurp);
		         
		         // ++ CD
		         Element Cd_CtgyPurp = doc.createElement("CD");
		         Cd_CtgyPurp.setTextContent(auxInfoPago.getCd_CtgyPurp());
		         CtgyPurp.appendChild(Cd_CtgyPurp);
		         
		         // + ReqdExctnDt
		         Element ReqdExctnDt = doc.createElement("ReqdExctnDt");
		         ReqdExctnDt.setTextContent(auxInfoPago.getReqdExctnDt2String()); /* dd-mm-yyyy */
		         PmtInf.appendChild(ReqdExctnDt);
		         
		         // + Dbtr
		         Element Dbtr = doc.createElement("Dbtr");
		         PmtInf.appendChild(Dbtr);
		         
		         // ++ Nm
		         Element Nm = doc.createElement("Nm");
		         Nm.setTextContent(auxInfoPago.getNm()); 
		         Dbtr.appendChild(Nm); 
		         
		         // ++ PstlAdr
		         Element PstlAdr = doc.createElement("PstlAdr");
		         Dbtr.appendChild(PstlAdr); 
		         
		         // +++ Ctry
		         Element Ctry = doc.createElement("Ctry");
		         Ctry.setTextContent(auxInfoPago.getCtry());
		         PstlAdr.appendChild(Ctry); 
		         
		         // +++ AdrLine (lineas 1 y 2)
		         Element AdrLine = doc.createElement("AdrLine");
		         AdrLine.setTextContent(auxInfoPago.getAdrLine());
		         PstlAdr.appendChild(AdrLine); 
		         Element AdrLine2 = doc.createElement("AdrLine2");
		         AdrLine2.setTextContent(auxInfoPago.getAdrLine2());
		         PstlAdr.appendChild(AdrLine2); 
		         
		         // ++ Id
		         Element Id_Dbtr = doc.createElement("Id");
		         Dbtr.appendChild(Id_Dbtr); 
		         
		         // +++ OrgId
		         Element OrgId = doc.createElement("OrgId");
		         Id_Dbtr.appendChild(OrgId); 
		         
		         // ++++ Othr_OrgId
		         Element Othr_OrgId = doc.createElement("Othr");
		         OrgId.appendChild(Othr_OrgId); 
		         
		         // +++++ Id_OrgId
		         Element Id_OrgId = doc.createElement("Id");
		         Id_OrgId.setTextContent(auxInfoPago.getId_Dbtr());
		         Othr_OrgId.appendChild(Id_OrgId);
		         
		         // + DbtrAcct
		         Element DbtrAcct = doc.createElement("DbtrAcct");
		         PmtInf.appendChild(DbtrAcct);
		         
		         // ++ Id_DbtrAcct
		         Element Id_DbtrAcct = doc.createElement("Id");
		         DbtrAcct.appendChild(Id_DbtrAcct); 
		         
		         // +++ Id_Iban
		         Element Id_Iban = doc.createElement("IBAN");
		         Id_DbtrAcct.appendChild(Id_Iban);
		         
		         // ++ IBAN
		         Element Iban = doc.createElement("IBAN");
		         Iban.setTextContent(auxInfoPago.getIBAN());
		         Id_Iban.appendChild(Iban);
		         
		         // ++ CCY
		         Element ccy = doc.createElement("Ccy");
		         ccy.setTextContent(auxInfoPago.getCcy());
		         DbtrAcct.appendChild(ccy);
		         
		         // + DbtrAgt
		         Element DbtrAgt = doc.createElement("DbtrAgt");
		         PmtInf.appendChild(DbtrAgt);
		         
		         // ++ FinInstnId
		         Element FinInstnId = doc.createElement("FinInstnId");
		         DbtrAgt.appendChild(FinInstnId); 
		         
		         // +++ BIC
		         Element bic = doc.createElement("BIC");
		         bic.setTextContent(auxInfoPago.getBIC());
		         FinInstnId.appendChild(bic);
		         
		         // + UltmtDbtr
		         Element UltmtDbtr = doc.createElement("UltmtDbtr");
		         PmtInf.appendChild(UltmtDbtr);
		         
		         // ++ Nm
		         Element nm = doc.createElement("Nm");
		         nm.setTextContent(auxInfoPago.getNm());
		         UltmtDbtr.appendChild(nm); 
		         
		          /*******************************************
		           *  LISTADO DE PAGOS
		           *******************************************/
		          
		          ArrayList<TransferPaymentItem> pagos = auxInfoPago.getPagos();
		          Iterator<TransferPaymentItem> iteradorPagos = pagos.iterator();
		          while(it.hasNext())
		          {
		        	  TransferPaymentItem pagoActual = iteradorPagos.next();
		        	  
			         // + CdtTrfTxInf
			         Element CdtTrfTxInf = doc.createElement("CdtTrfTxInf");
			         PmtInf.appendChild(CdtTrfTxInf);
			         
			         // ++ PmtId
			         Element PmtId = doc.createElement("PmtId");
			         CdtTrfTxInf.appendChild(PmtId);
	        	  
			         // +++ InstrId
			         Element InstrId = doc.createElement("InstrId");
			         InstrId.setTextContent(pagoActual.getInstrId());
			         PmtId.appendChild(InstrId); 
			         
			         // +++ EndToEndId
			         Element EndToEndId = doc.createElement("EndToEndId");
			         EndToEndId.setTextContent(pagoActual.getEndToEndId());
			         PmtId.appendChild(EndToEndId); 
			         
			         // ++ PmtTpInf
			         Element PmtTpInf_Pago = doc.createElement("PmtTpInf");
			         CdtTrfTxInf.appendChild(PmtTpInf_Pago);
			         
			         // +++ SvcLvl
			         Element SvcLvl_Pago = doc.createElement("SvcLvl");
			         PmtTpInf_Pago.appendChild(SvcLvl_Pago);
			         
			         // ++++ Cd
			         Element cd_SvcLvl = doc.createElement("Cd");
			         cd_SvcLvl.setTextContent(pagoActual.getCd_SvcLvl());
			         SvcLvl_Pago.appendChild(cd_SvcLvl); 
			         
			         // ++ Amt
			         Element Amt = doc.createElement("Amt");
			         CdtTrfTxInf.appendChild(Amt);
			         
			         // +++ InstdAmt Ccy="EUR"
			         Element InstdAmt = doc.createElement("InstdAmt");
			         Attr attr2 = doc.createAttribute("Ccy");
		             attr2.setValue(auxInfoPago.getCcy());
		             InstdAmt.setAttributeNode(attr2);
		             InstdAmt.setTextContent(pagoActual.getInstdAmt2String());
			         Amt.appendChild(InstdAmt);
			         
			         // ++ CdtrAgt
			         Element CdtrAgt = doc.createElement("CdtrAgt");
			         CdtTrfTxInf.appendChild(CdtrAgt);
			         
			         // +++ FinInstnId
			         Element FinInstnId_CdtrAgt = doc.createElement("FinInstnId");
			         CdtrAgt.appendChild(FinInstnId_CdtrAgt);
			         
			         // ++++ PstlAdr
			         Element PstlAdr_FinInstnId = doc.createElement("PstlAdr");
			         FinInstnId_CdtrAgt.appendChild(PstlAdr_FinInstnId);
			         
			         // +++++ Ctry
			         Element Ctry_PstlAdr = doc.createElement("Ctry");
			         Ctry_PstlAdr.setTextContent(pagoActual.getCtry_PstlAdr_Cdtr());
			         PstlAdr_FinInstnId.appendChild(Ctry_PstlAdr);
			         
			         // ++ Cdtr
			         Element Cdtr = doc.createElement("Cdtr");
			         CdtTrfTxInf.appendChild(Cdtr);
			         
			         // +++ Nm
			         Element Nm_Cdtr = doc.createElement("Cdtr");
			         Nm_Cdtr.setTextContent(pagoActual.getNm_Cdtr());
			         Cdtr.appendChild(Nm_Cdtr);
			         
			         // +++ PstlAdr
			         Element PstlAdr_Cdtr = doc.createElement("PstlAdr");
			         Cdtr.appendChild(PstlAdr_Cdtr);
			         
			         // ++++ Ctry
			         Element Ctry_PstlAdr_Cdtr = doc.createElement("Ctry");
			         Ctry_PstlAdr_Cdtr.setTextContent(pagoActual.getCtry_PstlAdr_Cdtr());
			         PstlAdr_Cdtr.appendChild(Ctry_PstlAdr_Cdtr);
			         
			         // ++++ AdrLine1
			         Element AdrLine1 = doc.createElement("AdrLine");
			         AdrLine1.setTextContent(pagoActual.getAdrLine_PstlAdr());
			         PstlAdr_Cdtr.appendChild(AdrLine1);
			         
			         // ++++ AdrLine2
			         Element AdrLine2_Cdtr = doc.createElement("AdrLine");
			         AdrLine2_Cdtr.setTextContent(pagoActual.getAdrLine2_PstlAdr());
			         PstlAdr_Cdtr.appendChild(AdrLine2_Cdtr);
			         
			         // ++ CdtrAcct
			         Element CdtrAcct = doc.createElement("CdtrAcct");
			         CdtTrfTxInf.appendChild(CdtrAcct);
			         
			         // +++ Id
			         Element Id_CdtrAcct = doc.createElement("Id");
			         CdtrAcct.appendChild(Id_CdtrAcct);
			         
			         // ++++ IBAN
			         Element IBAN_Id_CdtrAcct = doc.createElement("Id");
			         IBAN_Id_CdtrAcct.setTextContent(pagoActual.getIBAN());
			         Id_CdtrAcct.appendChild(IBAN_Id_CdtrAcct);
			         
			         // ++ Purp
			         Element Purp = doc.createElement("Purp");
			         CdtTrfTxInf.appendChild(Purp);
			         
			         // +++ Cd
			         Element Cd_Purp = doc.createElement("Cd");
			         Cd_Purp.setTextContent(pagoActual.getCd_Purp());
			         Purp.appendChild(Cd_Purp);
			         
			         // +++ RmtInf
			         Element RmtInf = doc.createElement("RmtInf");
			         CdtTrfTxInf.appendChild(RmtInf);
			         
			         // ++++ Ustrd
			         Element Ustrd = doc.createElement("Ustrd");
			         Ustrd.setTextContent(pagoActual.getUstrd());
			         RmtInf.appendChild(Ustrd);
		          }	  
	          }

   
	  // *************************************************************************************************************************************


	          // Write the XML File

	          TransformerFactory transformerFactory = TransformerFactory.newInstance();
	          Transformer transformer = transformerFactory.newTransformer();
	          DOMSource source = new DOMSource(doc);
	          StreamResult result = new StreamResult(new File(filePath));



	          transformer.transform(source, result);

	          System.out.println("File Created Succesfully!");

	        } catch (ParserConfigurationException pce) {
	          pce.printStackTrace();
	        } catch (TransformerException tfe) {
	          tfe.printStackTrace();
	        }
	      }
	}
	


package controller;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.security.GeneralSecurityException;
import java.security.ProviderException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import javax.xml.xpath.XPathExpressionException;

import pkcs.PKCS11Instance;
import pkcs.PKCS12Instance;
import sign.DigestAlgorithm;
import sign.PAdESSigner;
import sign.SignatureAppearance;
import timestamp.TSAAuthenticationType;
import timestamp.TimeStamp;
import timestamp.TimeStampType;
import utility.*;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JList;


/**
 * @author napon
 *
 */
public class main extends JFrame {

	private final JPanel contentPane;
	private final JTextField txtChoosePdfFile;
	private final JPasswordField pfPassword;
	private final FileNameExtensionFilter filter = new FileNameExtensionFilter("Folder or *.pdf", "pdf");
	private final FileNameExtensionFilter filterTp = new FileNameExtensionFilter("Xml template","xml");
	private final JTextField txtChooseGr;
	private ArrayList<String> listFile;
	private ArrayList<String> susFile;

//	private  DefaultListModel<JListValues> model ;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				main frame = new main();
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	public main() {
		setType(Type.POPUP);
		setResizable(false);
		setAutoRequestFocus(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setForeground(Color.WHITE);
		setFont(new Font("Kanit", Font.PLAIN, 16));
		setTitle("Sign Document");
		setBounds(100, 100, 709, 381);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 701, 345);
		contentPane.add(panel);
		panel.setLayout(null);

		final JScrollPane scrollPaneToDo = new JScrollPane();
		scrollPaneToDo.setBounds(73, 122, 266, 119);
		panel.add(scrollPaneToDo);

		final JScrollPane scrollPaneDone = new JScrollPane();
		scrollPaneDone.setBounds(364, 122, 266, 119);
		panel.add(scrollPaneDone);

		JLabel lblSignTimestamp = new JLabel("Sign Document");
		lblSignTimestamp.setFont(new Font("Kanit", Font.PLAIN, 20));
		lblSignTimestamp.setBounds(12, 23, 193, 23);
		panel.add(lblSignTimestamp);

		txtChoosePdfFile = new JTextField();
		txtChoosePdfFile.setFont(new Font("Kanit", Font.PLAIN, 13));
		txtChoosePdfFile.setEditable(false);
		txtChoosePdfFile.setText("Choose PDF File");
		txtChoosePdfFile.setBounds(147, 63, 460, 22);
		txtChoosePdfFile.setForeground(Color.black);
		panel.add(txtChoosePdfFile);
		txtChoosePdfFile.setColumns(10);

		//
		final JFileChooser jfcPDF = new JFileChooser(FileSystemView.getFileSystemView());
		JButton btnPDFFile = new JButton("Browse");
		btnPDFFile.setFont(new Font("Kanit", Font.PLAIN, 13));
		btnPDFFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				jfcPDF.setDialogTitle("Select file pdf");
				jfcPDF.setAcceptAllFileFilterUsed(false);
				jfcPDF.setMultiSelectionEnabled(true);
				jfcPDF.setFileSelectionMode(JFileChooser.FILES_ONLY);

				jfcPDF.addChoosableFileFilter(filter);
				jfcPDF.getSelectedFiles();

				int returnValue = jfcPDF.showOpenDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					String path = jfcPDF.getSelectedFile().getParentFile() + "";
					txtChoosePdfFile.setText(path);

					listFile = new ArrayList<String>();
					susFile = new ArrayList<String>();
					File[] namefile = jfcPDF.getSelectedFiles();
					for (int i = 0; i < jfcPDF.getSelectedFiles().length; i++) {
						listFile.add(namefile[i].getName());
					}
					String[] stringArray = listFile.toArray(new String[0]);
					JList listToDo = new JList(stringArray);
					scrollPaneToDo.setViewportView(listToDo);
				}
			}
		});
		btnPDFFile.setBounds(610, 62, 81, 24);
		panel.add(btnPDFFile);

		JLabel lblInputPasswordToken = new JLabel("Input Password Token : ");
		lblInputPasswordToken.setFont(new Font("Kanit", Font.PLAIN, 13));
		lblInputPasswordToken.setBounds(12, 290, 151, 16);
		panel.add(lblInputPasswordToken);

		pfPassword = new JPasswordField(20);
		pfPassword.setBounds(172, 288, 384, 22);
		panel.add(pfPassword);
		pfPassword.setColumns(10);

		final JCheckBox chckbxShowPassword = new JCheckBox("Show Password");
		chckbxShowPassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == chckbxShowPassword) {
					if (chckbxShowPassword.isSelected()) {
						pfPassword.setEchoChar((char) 0);
						pfPassword.requestFocus();
					} else {
						pfPassword.setEchoChar('*');
						pfPassword.requestFocus();
					}
				}
			}
		});
		chckbxShowPassword.setBounds(168, 314, 130, 25);
		panel.add(chckbxShowPassword);

		txtChooseGr = new JTextField();
		txtChooseGr.setText("Choose Template Graphic Signature");
		txtChooseGr.setForeground(Color.BLACK);
		txtChooseGr.setFont(new Font("Kanit", Font.PLAIN, 13));
		txtChooseGr.setEditable(false);
		txtChooseGr.setColumns(10);
		txtChooseGr.setBounds(147, 251, 460, 22);
		txtChooseGr.setEnabled(false);
		panel.add(txtChooseGr);

		final JFileChooser jfcTempGrapSig = new JFileChooser(FileSystemView.getFileSystemView());
		final JButton btnTempGrapSig = new JButton("Browse");
		btnTempGrapSig.setFont(new Font("Kanit", Font.PLAIN, 13));
		btnTempGrapSig.setBounds(610, 250, 81, 24);
		btnTempGrapSig.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				jfcTempGrapSig.setDialogTitle("Select file Template");
				jfcTempGrapSig.setAcceptAllFileFilterUsed(false);
				jfcTempGrapSig.setFileSelectionMode(JFileChooser.FILES_ONLY);

				jfcTempGrapSig.addChoosableFileFilter(filterTp);

				int returnValue = jfcTempGrapSig.showOpenDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					String word = jfcTempGrapSig.getSelectedFile().getPath();
					System.out.println(jfcTempGrapSig.getSelectedFile().getPath());
					txtChooseGr.setText(word);
				}
			}
		});
		btnTempGrapSig.setEnabled(false);
		panel.add(btnTempGrapSig);

		JLabel lblPdfFile = new JLabel("PDF File");
		lblPdfFile.setFont(new Font("Kanit", Font.PLAIN, 13));
		lblPdfFile.setBounds(10, 68, 130, 16);
		panel.add(lblPdfFile);

		JLabel lblGrapSig = new JLabel("Graphic Signature");
		lblGrapSig.setFont(new Font("Kanit", Font.PLAIN, 13));
		lblGrapSig.setBounds(12, 248, 130, 16);
		panel.add(lblGrapSig);

		JLabel lblTitleFileChoose = new JLabel("ไฟล์ที่เลือก");
		lblTitleFileChoose.setFont(new Font("Kanit", Font.PLAIN, 13));
		lblTitleFileChoose.setBounds(75, 106, 130, 16);
		panel.add(lblTitleFileChoose);

		JLabel lblTitleFileSuccess = new JLabel("ไฟล์ที่ลงนามสำเร็จ");
		lblTitleFileSuccess.setFont(new Font("Kanit", Font.PLAIN, 13));
		lblTitleFileSuccess.setBounds(364, 106, 130, 16);
		panel.add(lblTitleFileSuccess);

		final JCheckBox chcGS = new JCheckBox("Use");
		chcGS.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(chcGS.isSelected()) {
					txtChooseGr.setEnabled(true);
					btnTempGrapSig.setEnabled(true);
				}else {
					txtChooseGr.setEnabled(false);
					btnTempGrapSig.setEnabled(false);
				}
			}
		});
		chcGS.setBounds(12, 263, 130, 25);
		panel.add(chcGS);

		JButton btnSigntimestamp = new JButton("Sign");
		btnSigntimestamp.setFont(new Font("Kanit", Font.PLAIN, 13));
		btnSigntimestamp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					// config file
					config con = new config("resources/app2_config_sample.xml");
					
					File[] listFileInput = jfcPDF.getSelectedFiles();

					String passwordP11 = new String(pfPassword.getPassword());
					if (jfcPDF.getSelectedFile() != null) {
						if (passwordP11.length() != 0) {
							template tp;
							if (chcGS.isSelected()) {
								if (jfcTempGrapSig.getSelectedFile() != null) {
									tp = new template(jfcTempGrapSig.getSelectedFile().getAbsolutePath(),jfcTempGrapSig.getSelectedFile().getParentFile()+"");
									
									con.setSignSignatureVisibility("VISIBLE");
									System.setProperty("com.sun.security.enableAIAcaIssuers", "true");
									Locale.setDefault(new Locale("en", "UK"));

									String pattern = "yyyyMMdd_HHmmss";
									SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
									String date = "output_" + simpleDateFormat.format(new Date());
									System.out.println(date);
									new File(listFileInput[0].getParentFile() + "\\" + date).mkdir();

									for (int i = 0; i < listFileInput.length; i++) {
										runWithExternalInput(con, listFileInput[i], passwordP11, tp, date);
										listFile.remove(0);
										susFile.add(listFileInput[i].getName());
										String[] stringArrayDo = listFile.toArray(new String[0]);
										String[] stringArraySus = susFile.toArray(new String[0]);
										JList listToDo = new JList(stringArrayDo);
										JList listToSus = new JList(stringArraySus);
										scrollPaneToDo.setViewportView(listToDo);
										scrollPaneDone.setViewportView(listToSus);
									}
									System.out.println("********Sign And TimeStamp Done**********");
									JOptionPane.showMessageDialog(null, "Create Success \n"+listFileInput[0].getParentFile() + "\\" + date, "Success", JOptionPane.INFORMATION_MESSAGE);
									pfPassword.setEditable(false);
									jfcPDF.setSelectedFile(null);
								} else {
									JOptionPane.showMessageDialog(null, "กรุณาระบุ Graphic Signature Template","Required Field", JOptionPane.ERROR_MESSAGE);
								}
							} else {
								tp = new template();
								con.setSignSignatureVisibility("INVISIBLE");

								System.setProperty("com.sun.security.enableAIAcaIssuers", "true");
								Locale.setDefault(new Locale("en", "UK"));

								String pattern = "yyyyMMdd_HHmmss";
								SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
								String date = "output_" + simpleDateFormat.format(new Date());
								System.out.println(date);
								new File(listFileInput[0].getParentFile() + "\\" + date).mkdir();

								for (int i = 0; i < listFileInput.length; i++) {
									runWithExternalInput(con, listFileInput[i], passwordP11, tp, date);
									listFile.remove(0);
									susFile.add(listFileInput[i].getName());
									String[] stringArrayDo = listFile.toArray(new String[0]);
									String[] stringArraySus = susFile.toArray(new String[0]);
									JList listToDo = new JList(stringArrayDo);
									JList listToSus = new JList(stringArraySus);
									scrollPaneToDo.setViewportView(listToDo);
									scrollPaneDone.setViewportView(listToSus);
								}
								System.out.println("********Sign And TimeStamp Done**********");
								JOptionPane.showMessageDialog(null, "การลงนามเอกสารสำเร็จ \n ไฟล์อยู่ที่: "+listFileInput[0].getParentFile() + "\\" + date, "Success", JOptionPane.INFORMATION_MESSAGE);
								pfPassword.setEditable(false);
								jfcPDF.setSelectedFile(null);
							}

						} else {
							JOptionPane.showMessageDialog(null, "กรุณาระบุ รหัสของ Token ", "Required Field", JOptionPane.ERROR_MESSAGE);
						}
					} else {
						JOptionPane.showMessageDialog(null, "กรุณาระบุ ไฟล์ PDF", "Required Field", JOptionPane.ERROR_MESSAGE);
					}

				} catch (IOException e) {
					System.out.println("IOException____");
					e.printStackTrace();
					System.out.println("Erorr: " + e.getLocalizedMessage() + "Erorr");
					if (e.getLocalizedMessage() != null) {
						JOptionPane.showMessageDialog(null, "Password ไม่ถูกต้อง", "Required Field", JOptionPane.ERROR_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(null, " กรุณาปิดไฟล์ ที่จะทำการ Sign ", "Required Field", JOptionPane.ERROR_MESSAGE);
					}
				} catch (GeneralSecurityException e) {
					JOptionPane.showMessageDialog(null, "กรุณาเสียบ Token ", "Required ", JOptionPane.ERROR_MESSAGE);
					System.out.println("GeneralSecurityException____");
					e.printStackTrace();

				} catch (XPathExpressionException e) {
					System.out.println("XPathExpressionException____");
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, e.getLocalizedMessage(), "Required ", JOptionPane.ERROR_MESSAGE);
				} catch (ProviderException e) {
					JOptionPane.showMessageDialog(null, "กรุณาเสียบ Token ", "Required ", JOptionPane.ERROR_MESSAGE);
				} catch (Exception e) {
					Exception rootError= getErrorNa(e);
					StringWriter errors = new StringWriter();
					rootError.printStackTrace(new PrintWriter(errors));
					if (rootError.getMessage().indexOf("timestamp") != -1) {
						e.printStackTrace();
						JOptionPane.showMessageDialog(null, "ไม่สามารถเชื่อมต่อกับ Timestamp", "Error", JOptionPane.ERROR_MESSAGE);
						
					} else if (rootError.getMessage().contains("CKR_PIN_INCORRECT")) {
						e.printStackTrace();
						JOptionPane.showMessageDialog(null, "Password ไม่ถูกต้องกรุณาตรวจสอบใหม่", "Error", JOptionPane.ERROR_MESSAGE);
						
					}else if (errors.toString().indexOf("utility.template") != -1) {
						e.printStackTrace();
					JOptionPane.showMessageDialog(null,
							"กรุณาตรวจสอบไฟล์ config หรือ ไฟล์ Template \n Error: " + rootError.getLocalizedMessage(),
							"Required Field", JOptionPane.ERROR_MESSAGE);
						
					}else {
						System.out.println("Exception_____");
						e.printStackTrace();
						JOptionPane.showMessageDialog(null,"เกิดข้อผิดพลาด \n Error: " + rootError.getLocalizedMessage(),	"Required Field", JOptionPane.ERROR_MESSAGE);
					}
				}

			}
		});
		btnSigntimestamp.setBounds(556, 287, 138, 24);
		panel.add(btnSigntimestamp);
	}
	public Exception getErrorNa(Exception e) {
		Throwable cause = null;
		Throwable result = e;
		while(null != (cause = result.getCause())  && (result != cause) ) {
	        result = cause;
	    }
		return new Exception(result);
	}

	public static void runWithExternalInput(config con, File file, String pass11, template tp, String nameFolderDate)
			throws Exception {
		try {

			// Input-Output
			String signType = "single";
			String inputFile = file.getAbsolutePath();
			String outputFile = file.getParentFile() + "\\" + nameFolderDate + "\\Sign_" + file.getName();
			String inputFolder = null;
			String outputFolder = null;
			String outputSuffix = null;

			// PKCS12 Parameter
			String pkcs12FilePath = null;
			String pkcs12Password = null;

			// PKCS11 Parameter
			String pkcs11TokenName = con.getName();
			String pkcs11LibraryPath = con.getLibPath();
			String pkcs11TokenPin = pass11;
			String pkcs11KeyStorePassword = pass11;
			String pkcs11SearchKeyword = con.getPkcs11SearchKeyword();

			// TimeStamp URL
			TimeStampType timeStampingType = con.getTimeStampType();

			TSAAuthenticationType tsaAuthenticationType = con.getTsaAuthenticationType();
			String tsaURL = con.getUrl().equals("-") ? null : con.getUrl();
			String tsaUsername = con.getTsaUesrPassword_tsaUsername().equals("-") ? null
					: con.getTsaUesrPassword_tsaUsername();
			String tsaPassword = con.getTsaUesrPassword_tsaPassword().equals("-") ? null
					: con.getTsaUesrPassword_tsaPassword();
			String tsaPKCS12File = con.getTsaCertificateType_tsaPKCS12File().equals("-") ? null
					: con.getTsaCertificateType_tsaPKCS12File();
			String tsaPKCS12Password = con.getTsaUesrPassword_tsaPassword().equals("-") ? null
					: con.getTsaCertificateType_tsaPKCS12Password();

			TimeStamp timeStamping;
			switch (timeStampingType) {
			case COMPUTER_CLOCK:
				timeStamping = new TimeStamp(timeStampingType);
				break;
			case TSA:
				switch (tsaAuthenticationType) {
				case NO_AUTHENTICATION:
					timeStamping = new TimeStamp(timeStampingType, tsaURL, tsaAuthenticationType);
					break;
				case USERNAME_PASSWORD:
					timeStamping = new TimeStamp(timeStampingType, tsaURL, tsaAuthenticationType, tsaUsername,
							tsaPassword);
					break;
				case CERTIFICATE:
					timeStamping = new TimeStamp(timeStampingType, tsaURL, tsaAuthenticationType, tsaPKCS12File,
							tsaPKCS12Password);
					break;
				default:
					throw new Exception("TSA authentication must be input");
				}
				break;
			default:
				timeStamping = new TimeStamp(TimeStampType.COMPUTER_CLOCK);
			}

			// Signature Appearance
			SignatureAppearance signatureAppearance = new SignatureAppearance();
			signatureAppearance.setLocation(con.getSignLocation());
			signatureAppearance.setReason(con.getSignReason());
			signatureAppearance.setX(tp.getXbar());
			signatureAppearance.setY(tp.getYbar());
			signatureAppearance.setWidth(tp.getWv());
			signatureAppearance.setHeight(tp.getHv());
			signatureAppearance.setSignatureFieldName(null);
			signatureAppearance.setPageNumber(tp.getpNumber());
			signatureAppearance.setSignatureLevel(con.getSignSignatureLevel());
			signatureAppearance.setSignatureVisibility(con.getSignSignatureVisibility());
			signatureAppearance.setSignaturePattern(con.getSignSignaturePattern());
			signatureAppearance.setSignatureImage(tp.getGsPath());
			// Other sign parameter
			DigestAlgorithm digestAlgorithm = DigestAlgorithm.SHA256;

			// PKCS
			PKCS12Instance pkcs12 = null;
			PKCS11Instance pkcs11 = null;

			if (pkcs12FilePath != null && pkcs12Password != null) {
				pkcs12 = new PKCS12Instance(pkcs12FilePath, pkcs12Password);
			} else if (pkcs11TokenName != null && pkcs11LibraryPath != null && pkcs11TokenPin != null
					&& pkcs11KeyStorePassword != null && pkcs11SearchKeyword != null) {
				pkcs11 = new PKCS11Instance(pkcs11TokenName, pkcs11LibraryPath, pkcs11TokenPin, pkcs11KeyStorePassword,
						pkcs11SearchKeyword);
			} else {
				throw new Exception("Incomplete certificate input");
			}

			// Let's sign
			PAdESSigner padesSigner = new PAdESSigner();
			if (signType.equalsIgnoreCase("single")) {
				if (pkcs12 != null) {
					padesSigner.signOnce(inputFile, outputFile, pkcs12, digestAlgorithm, signatureAppearance,
							timeStamping);
				} else if ((pkcs11 != null)) {
					padesSigner.signOnce(inputFile, outputFile, pkcs11, digestAlgorithm, signatureAppearance,
							timeStamping);
				}
			} else if (signType.equalsIgnoreCase("multiple")) {
				if (pkcs12 != null) {
					padesSigner.signMultiple(inputFolder, outputFolder, outputSuffix, pkcs12, digestAlgorithm,
							signatureAppearance, timeStamping);
				} else if ((pkcs11 != null)) {
					padesSigner.signMultiple(inputFolder, outputFolder, outputSuffix, pkcs11, digestAlgorithm,
							signatureAppearance, timeStamping);
				}
			} else {
				throw new Exception("Sign type must be 'single' or 'multiple only'");
			}

			System.out.println("Complete");
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception(ex);
		}
	}
}

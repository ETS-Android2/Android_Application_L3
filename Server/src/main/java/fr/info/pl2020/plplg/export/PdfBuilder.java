package fr.info.pl2020.plplg.export;

import com.itextpdf.io.font.FontProgram;
import com.itextpdf.io.font.FontProgramFactory;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.property.AreaBreakType;
import com.itextpdf.layout.property.TextAlignment;
import fr.info.pl2020.plplg.util.FunctionsUtils;
import org.jboss.logging.Logger;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class PdfBuilder {

    private static Logger logger = Logger.getLogger(PdfBuilder.class);
    private static final String LOGO_LOCATION = "src/main/resources/static/logo_unice.png";
    private static final float DEFAULT_FONT_SIZE = 10;
    private static final float DEFAULT_MARGIN_SIZE = 6;
    private static final float DEFAULT_FIXED_LEADING_SIZE = 10;
    private static final String SUBJECT_PREFIX = "Objet : ";

    public static final String[] DEFAULT_SENDER_INFOS = new String[]{"Faculté des Sciences", "28, Av Valrose", "06108 NICE"};

    private final File file;
    private final Document document;
    private PdfFont font;
    private PdfFont font_bold;

    private String[] recipientInfos;
    private String[] senderInfos;
    private String subject;
    private Div body = new Div();

    public PdfBuilder() throws IOException {
        this.file = File.createTempFile("plplg", ".pdf");
        this.file.deleteOnExit();

        PdfWriter writer = new PdfWriter(this.file.getAbsoluteFile());
        PdfDocument pdfDocument = new PdfDocument(writer);
        this.document = new Document(pdfDocument);

        initFont();
        this.document.setFont(this.font);
    }

    public PdfBuilder setRecipientInfos(String[] recipientInfos) {
        this.recipientInfos = recipientInfos;
        return this;
    }

    public PdfBuilder setSenderInfos(String[] senderInfos) {
        this.senderInfos = senderInfos;
        return this;
    }

    public PdfBuilder setSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public File build() throws IOException {
        buildHeader();
        if (!FunctionsUtils.isNullOrBlank(this.subject)) {
            buildSubject();
        }

        buildBody();

        this.document.close();
        return this.file;
    }

    private void initFont() throws IOException {
        try {
            FontProgram fontProgram = FontProgramFactory.createFont("fonts/arial.ttf");
            this.font = PdfFontFactory.createFont(fontProgram, PdfEncodings.WINANSI, true);
        } catch (IOException e) {
            logger.warn("Echec de la récupération de la police d'écriture 'arial' : " + e.getMessage());
            this.font = PdfFontFactory.createFont(StandardFonts.COURIER);
        }

        try {
            FontProgram fontProgram = FontProgramFactory.createFont("fonts/arialbd.ttf");
            this.font_bold = PdfFontFactory.createFont(fontProgram, PdfEncodings.WINANSI, true);
        } catch (IOException e) {
            logger.warn("Echec de la récupération de la police d'écriture 'arialbd' : " + e.getMessage());
            this.font_bold = PdfFontFactory.createFont(StandardFonts.COURIER_BOLD);
        }
    }


    /*** HEADER ***/

    private void buildHeader() throws IOException {
        writeDateHeader();
        writeLogo();

        if (this.senderInfos != null && this.senderInfos.length != 0) {
            writeSender();
        }

        if (this.recipientInfos != null && this.recipientInfos.length != 0) {
            writeRecipient();
        }
    }

    private void writeDateHeader() {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String strDate = formatter.format(date);

        Paragraph p = new Paragraph()
                .setTextAlignment(TextAlignment.RIGHT)
                .setFontSize(DEFAULT_FONT_SIZE)
                .setFixedLeading(0)
                .setMargins(12, 5, 0, 0)
                .add("Nice, le " + strDate);

        this.document.add(p);
    }

    private void writeLogo() throws MalformedURLException {
        Image logo = new Image(ImageDataFactory.create(LOGO_LOCATION));
        logo.scaleAbsolute(142, 112);

        Paragraph p = new Paragraph()
                .setMargins(0, 0, 13, DEFAULT_MARGIN_SIZE)
                .add(logo);

        this.document.add(p);
    }

    private void writeSender() {
        Paragraph p = new Paragraph()
                .setMarginLeft(DEFAULT_MARGIN_SIZE)
                .setFontSize(DEFAULT_FONT_SIZE)
                .setFixedLeading(DEFAULT_FIXED_LEADING_SIZE)
                .setTextAlignment(TextAlignment.LEFT)
                .add(String.join("\n", this.senderInfos));

        this.document.add(p);
    }

    private void writeRecipient() {
        Paragraph p = new Paragraph()
                .setMarginLeft(275)
                .setFontSize(DEFAULT_FONT_SIZE)
                .setFixedLeading(DEFAULT_FIXED_LEADING_SIZE)
                .setTextAlignment(TextAlignment.LEFT)
                .add(String.join("\n", this.recipientInfos));

        this.document.add(p);
    }


    /*** SUBJECT ***/

    private void buildSubject() {
        Paragraph p = new Paragraph()
                .setMarginTop(30)
                .setFontSize(DEFAULT_FONT_SIZE)
                .setFont(this.font_bold)
                .add(SUBJECT_PREFIX + this.subject);

        this.document.add(p);
    }

    /*** BODY **/

    public class BodyElement {
        private Div body;

        private BodyElement() {
            this.body = new Div();
        }

        public BodyElement add(String text) {
            this.body.add(new Paragraph().add(text).setTextAlignment(TextAlignment.JUSTIFIED));
            return this;
        }

        public BodyElement add(Iterable<?> iterable) {
            this.body.add(createBulletList(iterable, 0));
            return this;
        }

        public BodyElement add(Map<?, ?> map) {
            this.body.add(createCustomList(map, 0));
            return this;
        }

        public BodyElement add(IBlockElement e) {
            this.body.add(e);
            return this;
        }

        private List createCustomList(Map<?, ?> map, int level) {
            List list = new List().setListSymbol("");

            map.forEach((o, o2) -> {
                Paragraph p = new Paragraph();
                if (o instanceof Text) {
                    p.add((Text) o);
                } else {
                    p.add(new Text(o.toString()).setBold());
                }

                if (o2 instanceof Map) {
                    p.add(createCustomList((Map<?, ?>) o2, level + 1));
                } else if (o2 instanceof Iterable) {
                    p.add(createBulletList((Iterable<?>) o2, level + 1));
                } else {
                    p.add(o2.toString());
                }

                ListItem listItem = new ListItem();
                listItem.add(p);
                list.add(listItem);
            });

            return list;
        }

        private List createBulletList(Iterable<?> iterable, int level) {
            List list = new List();
            if (level % 2 == 0) {
                list.setListSymbol("\u2022");
            }

            iterable.forEach(o -> {
                ListItem listItem = new ListItem();
                if (o instanceof Map) {
                    listItem.add(createCustomList((Map<?, ?>) o, level + 1));
                } else if (o instanceof Iterable) {
                    listItem.add(createBulletList((Iterable<?>) o, level + 1));
                } else if (o instanceof IBlockElement) {
                    listItem.add((IBlockElement) o);
                } else {
                    listItem.add(new Paragraph().add(o.toString()));
                }
                list.add(listItem);
            });

            return list;
        }

        public BodyElement addBlank() {
            this.body.add(new Paragraph().add("\n"));
            return this;
        }

        public BodyElement nextPage() {
            this.body.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
            return this;
        }

        public PdfBuilder endBody() {
            PdfBuilder.this.body = this.body;
            return PdfBuilder.this;
        }
    }

    public BodyElement startBody() {
        return new BodyElement();
    }

    private void buildBody() {
        this.body.setFontSize(DEFAULT_FONT_SIZE);
        this.document.add(this.body);
    }
}

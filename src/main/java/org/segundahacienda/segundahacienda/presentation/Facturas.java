package org.segundahacienda.segundahacienda.presentation;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Div;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.segundahacienda.segundahacienda.logic.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@RestController
@RequestMapping("/api/facturas")
public class Facturas {
    @Autowired
    Service service;

    @GetMapping("/facturas")
    public List<Factura> read(@RequestParam String cred){
        List<Factura> lista = service.lista_facturas_proveedor(cred);

        for(Factura f : lista){
            f.setTotal(service.total_factura(f.getNum_factura()));
        }

        return lista;
    }

    @GetMapping("/buscar")
    public List<Factura> buscar(@RequestParam("num") int num, @RequestParam("cred") String cred){
        return service.buscar_factura_especifica(num, cred);
    }

    /*@GetMapping("/facturas")
    public String show(Model model, HttpSession httpSession){
        Usuario usuario = (Usuario) httpSession.getAttribute("usuario");
        model.addAttribute("buscador", new Factura());
        model.addAttribute("facturas", service.lista_facturas_proveedor(usuario.getCredencial()));
        model.addAttribute("service", service);
        return "/facturas/facturas";
    }

   *//* @GetMapping("/buscar")
    public String busqueda(@ModelAttribute Factura factura, Model model, HttpSession httpSession){
        Usuario usuario = (Usuario) httpSession.getAttribute("usuario");
        if(factura.getNum_factura() > 0){
            model.addAttribute("buscador", new Factura());
            model.addAttribute("facturas", service.buscar_factura(factura.getNum_factura()));
            model.addAttribute("service", service);
            return "/facturas/facturas";

        }else{
            return "redirect:/facturas";
        }
    }*//*

    */
    @GetMapping("/pdf")
    public void pdf(@RequestParam("num_factura") int numFactura, @RequestParam("cred") String cred, HttpServletResponse response) {
        try {
            Iterable<Producto> productos = service.buscar_productos_factura(numFactura);
            Proveedor proveedor = service.buscar_proveedor(cred);
            Factura factura = service.buscar_factura(numFactura);

            response.setContentType("application/pdf");
            PdfWriter writer = new PdfWriter(response.getOutputStream());
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf, PageSize.A4);

            document.add(new Paragraph("Factura").setTextAlignment(TextAlignment.CENTER)); // Centrar el texto "Factura"
            document.add(new Paragraph("Número de factura: " + factura.getNum_factura()).setTextAlignment(TextAlignment.RIGHT));
            document.add(new Paragraph("Fecha de emision: " + factura.getFecha_emision()));
            document.add(new Paragraph("Emisor: " + proveedor.getNombre()).setTextAlignment(TextAlignment.RIGHT));
            document.add(new Paragraph("Identificacion: " + proveedor.getIdentificador()).setTextAlignment(TextAlignment.RIGHT));
            document.add(new Paragraph("Receptor: " + factura.getFacturaByCliente().getNombre()).setTextAlignment(TextAlignment.RIGHT));
            document.add(new Paragraph("Identificacion: " + factura.getFacturaByCliente().getIdentificacion()).setTextAlignment(TextAlignment.RIGHT));

            Table tabla = new Table(3); // 3 columnas para Código, Descripción y Precio

            tabla.addHeaderCell("Código");
            tabla.addHeaderCell("Descripción");
            tabla.addHeaderCell("Precio");

            for (Producto producto : productos) {
                tabla.addCell(producto.getCodigoProducto() + "");
                tabla.addCell(producto.getDescripcion());
                tabla.addCell(String.valueOf(producto.getPrecio()));
            }

            Div contenedorTablaCentrada = new Div().setTextAlignment(TextAlignment.CENTER);
            contenedorTablaCentrada.add(tabla);
            document.add(new Paragraph("Productos:")); // Centrar el texto "Productos"
            document.add(contenedorTablaCentrada);

            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/xml")
    public void xml(@RequestParam("num_factura") int numFactura, @RequestParam("cred") String cred, HttpServletResponse res) throws IOException {
        Iterable<Producto> productos = service.buscar_productos_factura(numFactura);
        Proveedor proveedor = service.buscar_proveedor(cred);
        Factura factura = service.buscar_factura(numFactura);

        res.setContentType("application/xml");
        res.setCharacterEncoding("UTF-8");

        PrintWriter writer = res.getWriter();
        writer.print(buildXml(factura, proveedor, productos));
        writer.close();
    }

    private String buildXml(Factura factura, Proveedor proveedor, Iterable<Producto> productos) {
        StringBuilder xml = new StringBuilder();
        xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        xml.append("<factura>");
        xml.append("<numero>").append(factura.getNum_factura()).append("</numero>");
        xml.append("<fecha>").append(factura.getFecha_emision()).append("</fecha>");
        xml.append("<emisor>");
        xml.append("<nombre>").append(proveedor.getNombre()).append("</nombre>");
        xml.append("<identificacion>").append(proveedor.getIdentificador()).append("</identificacion>");
        xml.append("</emisor>");
        xml.append("<receptor>");
        xml.append("<nombre>").append(factura.getFacturaByCliente().getNombre()).append("</nombre>");
        xml.append("<identificacion>").append(factura.getFacturaByCliente().getIdentificacion()).append("</identificacion>");
        xml.append("</receptor>");
        xml.append("<productos>");

        for (Producto producto : productos) {
            xml.append("<producto>");
            xml.append("<codigo>").append(producto.getCodigoProducto()).append("</codigo>");
            xml.append("<descripcion>").append(producto.getDescripcion()).append("</descripcion>");
            xml.append("<precio>").append(producto.getPrecio()).append("</precio>");
            xml.append("</producto>");
        }

        xml.append("</productos>");
        xml.append("</factura>");

        return xml.toString();
    }

//    @GetMapping("/facturas/xml")
//    public void xml(@RequestParam("num_factura") int numFactura, HttpServletResponse res, HttpSession httpSession) throws IOException {
//        Usuario usuario = (Usuario) httpSession.getAttribute("usuario");
//        Iterable<Producto> productos = service.buscar_productos_factura(numFactura);
//        Proveedor proveedor = service.buscar_proveedor(usuario.getCredencial());
//        Factura factura = service.buscar_factura(numFactura);
//        SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
//        resolver.setApplicationContext(new AnnotationConfigApplicationContext());
//        resolver.setPrefix("classpath:/templates/");
//        resolver.setSuffix(".xml");
//        resolver.setCharacterEncoding("UTF-8");
//        resolver.setTemplateMode(TemplateMode.XML);
//        SpringTemplateEngine engine = new SpringTemplateEngine();
//        engine.setTemplateResolver(resolver);
//        Context ctx = new Context();
//        ctx.setVariable("factura", factura);
//        ctx.setVariable("productos", productos);
//        ctx.setVariable("proveedor", proveedor);
//        String xml = engine.process("/facturas/XmlView", ctx);
//        res.setContentType("application/xml");
//        PrintWriter writer = res.getWriter();
//        writer.print(xml);
//        writer.close();
//    }
}

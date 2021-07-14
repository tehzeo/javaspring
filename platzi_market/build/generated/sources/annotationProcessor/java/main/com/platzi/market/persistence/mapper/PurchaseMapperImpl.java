package com.platzi.market.persistence.mapper;

import com.platzi.market.domain.Purchase;
import com.platzi.market.domain.PurchaseItem;
import com.platzi.market.persistence.entity.Compra;
import com.platzi.market.persistence.entity.ComprasProducto;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-07-14T08:04:20-0700",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-6.4.1.jar, environment: Java 11.0.11 (AdoptOpenJDK)"
)
@Component
public class PurchaseMapperImpl implements PurchaseMapper {

    @Autowired
    private PurchaseItemMapper purchaseItemMapper;

    @Override
    public Purchase toPurchase(Compra compra) {
        if ( compra == null ) {
            return null;
        }

        Purchase purchase = new Purchase();

        if ( compra.getIdCompra() != null ) {
            purchase.setPurchaseId( Integer.parseInt( compra.getIdCompra() ) );
        }
        purchase.setClientId( compra.getIdCliente() );
        purchase.setDate( compra.getFecha() );
        purchase.setPaymentMethod( compra.getMedioPago() );
        purchase.setComment( compra.getComentario() );
        purchase.setState( compra.getEstado() );
        if ( purchase.getItems() != null ) {
            List<PurchaseItem> list = comprasProductoListToPurchaseItemList( compra.getProductos() );
            if ( list != null ) {
                purchase.getItems().addAll( list );
            }
        }

        return purchase;
    }

    @Override
    public List<Purchase> toPurchases(List<Compra> compras) {
        if ( compras == null ) {
            return null;
        }

        List<Purchase> list = new ArrayList<Purchase>( compras.size() );
        for ( Compra compra : compras ) {
            list.add( toPurchase( compra ) );
        }

        return list;
    }

    @Override
    public Compra toCompra(Purchase purchase) {
        if ( purchase == null ) {
            return null;
        }

        Compra compra = new Compra();

        compra.setIdCompra( String.valueOf( purchase.getPurchaseId() ) );
        compra.setIdCliente( purchase.getClientId() );
        compra.setFecha( purchase.getDate() );
        compra.setMedioPago( purchase.getPaymentMethod() );
        compra.setComentario( purchase.getComment() );
        compra.setEstado( purchase.getState() );
        compra.setProductos( purchaseItemListToComprasProductoList( purchase.getItems() ) );

        return compra;
    }

    protected List<PurchaseItem> comprasProductoListToPurchaseItemList(List<ComprasProducto> list) {
        if ( list == null ) {
            return null;
        }

        List<PurchaseItem> list1 = new ArrayList<PurchaseItem>( list.size() );
        for ( ComprasProducto comprasProducto : list ) {
            list1.add( purchaseItemMapper.toPurchaseItem( comprasProducto ) );
        }

        return list1;
    }

    protected List<ComprasProducto> purchaseItemListToComprasProductoList(List<PurchaseItem> list) {
        if ( list == null ) {
            return null;
        }

        List<ComprasProducto> list1 = new ArrayList<ComprasProducto>( list.size() );
        for ( PurchaseItem purchaseItem : list ) {
            list1.add( purchaseItemMapper.toComprasProducto( purchaseItem ) );
        }

        return list1;
    }
}

package com.example.garacar.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.garacar.BookingActivity;
import com.example.garacar.R;
import com.example.garacar.ServiceDetailActivity;
import com.example.garacar.models.Product;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private Context context;
    private List<Product> productList;

    public ProductAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product p = productList.get(position);

        holder.productName.setText(p.getServiceName());
        holder.productPrice.setText(String.format("%,.0fđ", p.getServicePrice()));
        holder.ratingBar.setRating(p.getRating());

        if (p.isDiscounted()) {
            holder.discountText.setVisibility(View.VISIBLE);
            holder.discountText.setText("-" + p.getDiscountPercentage() + "%");
        } else {
            holder.discountText.setVisibility(View.GONE);
        }

        Glide.with(context).load(p.getServiceImage()).into(holder.productImage);

        // 🟡 Khi người dùng click vào sản phẩm
        holder.itemView.setOnClickListener(v -> {
//            Intent intent = new Intent(context, BookingActivity.class);
            Intent intent = new Intent(context, ServiceDetailActivity.class);
            intent.putExtra("serviceName", p.getServiceName());
            intent.putExtra("serviceImage", p.getServiceImage()); // thêm ảnh
            intent.putExtra("price", p.getServicePrice());        // thêm giá
            intent.putExtra("serviceDesc", p.getServiceDesc());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView productName, productPrice, discountText;
        RatingBar ratingBar;
        LinearLayout discountContainer;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.productImage_singleProduct);
            productName = itemView.findViewById(R.id.productName_singleProduct);
            productPrice = itemView.findViewById(R.id.productPrice_singleProduct);
            discountText = itemView.findViewById(R.id.discountTv_singleProduct);
            ratingBar = itemView.findViewById(R.id.productRating_singleProduct);
            discountContainer = itemView.findViewById(R.id.discount_singleProduct);
        }
    }
}


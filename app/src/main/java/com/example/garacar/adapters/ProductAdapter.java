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
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.garacar.BookingActivity;
import com.example.garacar.R;
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
        Product product = productList.get(position);

        // Hiển thị thông tin sản phẩm
        holder.productName.setText(product.getName());
        holder.productPrice.setText(String.format("$%.2f", product.getPrice()));
        Glide.with(context)
                .load(product.getImageUrl())
                .placeholder(R.drawable.car)
                .into(holder.productImage);
        holder.productRating.setRating(product.getRating());

        // Hiển thị giảm giá nếu có
        if (product.isDiscounted()) {
            holder.discountLayout.setVisibility(View.VISIBLE);
            holder.discountText.setText(String.format("%d%%", product.getDiscountPercentage()));
        } else {
            holder.discountLayout.setVisibility(View.GONE);
        }

        // Xử lý click để mở BookingActivity, truyền tên dịch vụ
        holder.cardView.setOnClickListener(v -> {
            Intent intent = new Intent(context, BookingActivity.class);
            intent.putExtra("selectedService", product.getName());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView productImage, productAddToFav;
        TextView productName, productPrice, productBrandName, discountText;
        RatingBar productRating;
        LinearLayout discountLayout;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);
            productImage = itemView.findViewById(R.id.productImage_singleProduct);
            productAddToFav = itemView.findViewById(R.id.productAddToFav_singleProduct);
            productName = itemView.findViewById(R.id.productName_singleProduct);
            productPrice = itemView.findViewById(R.id.productPrice_singleProduct);
            productBrandName = itemView.findViewById(R.id.productBrandName_singleProduct);
            productRating = itemView.findViewById(R.id.productRating_singleProduct);
            discountLayout = itemView.findViewById(R.id.discount_singleProduct);
            discountText = itemView.findViewById(R.id.discountTv_singleProduct);
        }
    }
}

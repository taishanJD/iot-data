package com.quarkdata.data.model.mongo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection="orders")
public class Order implements Serializable {

    /**
     * @ID 生成MongoDB文档的_id 内容，如果不指定，MongoDB 会主动生成一个
     */
    @Id
    private String id;

    /**
     * @Field 映射成MongoDB文档的字段内容
     */
    @Field("onumber")
    private String number;

    /**
     * @Indexed 是否在该字段上加上索引
     */
    @Field("date")
    private Date date;
    
    @Field("cname")
    private String name;
    

    /**
     * 集合类型最好使用 ? 不确定类型(或者说任意类型)
     * 否则会info（Found cycle for field 'itemList' in type 'Order' for path ''）表明你的代码中有潜在的循环使用
     * 
     * 像这样有另一个对象的集合，另一个对象不用加任何的MongoDB 注释
     */
    @Field("item")
    private Item item;

    @Field("add")
    private String add;
    

	public String getAdd() {
		return add;
	}


	public void setAdd(String add) {
		this.add = add;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getNumber() {
		return number;
	}


	public void setNumber(String number) {
		this.number = number;
	}


	public Date getDate() {
		return date;
	}


	public void setDate(Date date) {
		this.date = date;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}
	
	public Item getItem() {
		return item;
	}


	public void setItem(Item item) {
		this.item = item;
	}




	class Item implements Serializable {
		
		private int quantty;
		
		private int price;
		
		@Field("pnumber")
		private String pNumber;
		
		public int getQuantity() {
			return quantty;
		}
		public void setQuantity(int quantity) {
			this.quantty = quantity;
		}
		public int getPrice() {
			return price;
		}
		public void setPrice(int price) {
			this.price = price;
		}
		public String getpNumber() {
			return pNumber;
		}
		public void setpNumber(String pNumber) {
			this.pNumber = pNumber;
		}
	}
}
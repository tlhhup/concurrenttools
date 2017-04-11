package org.tlh.concurrenttools;

import java.io.FileOutputStream;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class CountDownLatchDemo {

	// 设置使用的线程的数量
	private static final int THREAD_COUNT = 10;

	// 创建countDownLatch对象并设置fork的任务个数
	private static CountDownLatch countDownLatch = new CountDownLatch(THREAD_COUNT);

	public static void main(String[] args) throws Exception {
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("sheet1");

		ExecutorService threadPool = Executors.newFixedThreadPool(THREAD_COUNT);
		int start, end, total = 50, pre = total / THREAD_COUNT;

		for (int i = 0; i < THREAD_COUNT; i++) {
			start = i * pre;
			end = (i + 1) * pre;
			if (end > total) {
				end = total;
			}
			threadPool.execute(new ExportExcel(start, end, sheet));
		}
		try {
			// 阻塞当前线程，直到任务数位0
			countDownLatch.await();
			// 将数据写入到文件中
			FileOutputStream stream = new FileOutputStream("countdownlatch.xls");
			workbook.write(stream);
			stream.close();
		} finally {
			// 结束任务
			threadPool.shutdown();
		}
	}

	private static class ExportExcel implements Runnable {
		int start, end;
		HSSFSheet sheet;

		public ExportExcel(int start, int end, HSSFSheet sheet) {
			this.start = start;
			this.end = end;
			this.sheet = sheet;
		}

		public void run() {
			// 写入数据
			for (; start < end; start++) {
				HSSFRow row = sheet.getRow(start);
				if (row == null) {
					row = sheet.createRow(start);
				}
				for (int i = 0; i < 2; i++) {
					HSSFCell cell = row.getCell(i);
					if (cell == null) {
						cell = row.createCell(i);
					}
					cell.setCellValue("第" + start + "行，第" + i + "列数据");
				}
			}
			// 执行完之后减少一个任务
			countDownLatch.countDown();
		}
	}

}

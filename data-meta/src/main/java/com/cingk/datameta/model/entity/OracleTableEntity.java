package com.cingk.datameta.model.entity;

import com.cingk.datameta.model.ITableEntity;

import java.sql.Timestamp;

public class OracleTableEntity  implements ITableEntity {

    private String owner;
    private String tableName;
    private String tablespaceName;
    private String clusterName;
    private String iotName;
    private String status;
    private Double pctFree;
    private Double pctUsed;
    private Double iniTrans;
    private Double maxTrans;
    private Double initialExtent;
    private Double nextExtent;
    private Double minExtent;
    private Double maxExtent;
    private Double pctIncrease;
    private Double freelists;
    private Double freelistGroups;
    private String logging;
    private String backedUp;
    private Double numRows;
    private Double blocks;
    private Double emptyBlocks;
    private Double avgSpace;
    private Double chainCnt;
    private Double avgRowLen;
    private Double avgSpaceFreelistBlocks;
    private Double numFreelistBlocks;
    private Double degree;
    private Double instances;
    private String cache;
    private String tableLock;
    private Double sampleSize;
    private Timestamp lastAnalyzed;
    private String partitioned;
    private String iotType;
    private String temporary;
    private String secondary;
    private String nested;
    private String bufferPool;
    private String flushCache;
    private String cellFlashCache;
    private String rowMovement;
    private String globalStats;
    private String userStats;
    private String duration;
    private String skipCorrupt;
    private String monitoring;
    private String clusterOwner;
    private String dependencies;
    private String compression;
    private String compressFor;
    private String dropped;
    private String readOnly;
    private String segmentCreated;
    private String resultCache;

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTablespaceName() {
        return tablespaceName;
    }

    public void setTablespaceName(String tablespaceName) {
        this.tablespaceName = tablespaceName;
    }

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    public String getIotName() {
        return iotName;
    }

    public void setIotName(String iotName) {
        this.iotName = iotName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getPctFree() {
        return pctFree;
    }

    public void setPctFree(Double pctFree) {
        this.pctFree = pctFree;
    }

    public Double getPctUsed() {
        return pctUsed;
    }

    public void setPctUsed(Double pctUsed) {
        this.pctUsed = pctUsed;
    }

    public Double getIniTrans() {
        return iniTrans;
    }

    public void setIniTrans(Double iniTrans) {
        this.iniTrans = iniTrans;
    }

    public Double getMaxTrans() {
        return maxTrans;
    }

    public void setMaxTrans(Double maxTrans) {
        this.maxTrans = maxTrans;
    }

    public Double getInitialExtent() {
        return initialExtent;
    }

    public void setInitialExtent(Double initialExtent) {
        this.initialExtent = initialExtent;
    }

    public Double getNextExtent() {
        return nextExtent;
    }

    public void setNextExtent(Double nextExtent) {
        this.nextExtent = nextExtent;
    }

    public Double getMinExtent() {
        return minExtent;
    }

    public void setMinExtent(Double minExtent) {
        this.minExtent = minExtent;
    }

    public Double getMaxExtent() {
        return maxExtent;
    }

    public void setMaxExtent(Double maxExtent) {
        this.maxExtent = maxExtent;
    }

    public Double getPctIncrease() {
        return pctIncrease;
    }

    public void setPctIncrease(Double pctIncrease) {
        this.pctIncrease = pctIncrease;
    }

    public Double getFreelists() {
        return freelists;
    }

    public void setFreelists(Double freelists) {
        this.freelists = freelists;
    }

    public Double getFreelistGroups() {
        return freelistGroups;
    }

    public void setFreelistGroups(Double freelistGroups) {
        this.freelistGroups = freelistGroups;
    }

    public String getLogging() {
        return logging;
    }

    public void setLogging(String logging) {
        this.logging = logging;
    }

    public String getBackedUp() {
        return backedUp;
    }

    public void setBackedUp(String backedUp) {
        this.backedUp = backedUp;
    }

    public Double getNumRows() {
        return numRows;
    }

    public void setNumRows(Double numRows) {
        this.numRows = numRows;
    }

    public Double getBlocks() {
        return blocks;
    }

    public void setBlocks(Double blocks) {
        this.blocks = blocks;
    }

    public Double getEmptyBlocks() {
        return emptyBlocks;
    }

    public void setEmptyBlocks(Double emptyBlocks) {
        this.emptyBlocks = emptyBlocks;
    }

    public Double getAvgSpace() {
        return avgSpace;
    }

    public void setAvgSpace(Double avgSpace) {
        this.avgSpace = avgSpace;
    }

    public Double getChainCnt() {
        return chainCnt;
    }

    public void setChainCnt(Double chainCnt) {
        this.chainCnt = chainCnt;
    }

    public Double getAvgRowLen() {
        return avgRowLen;
    }

    public void setAvgRowLen(Double avgRowLen) {
        this.avgRowLen = avgRowLen;
    }

    public Double getAvgSpaceFreelistBlocks() {
        return avgSpaceFreelistBlocks;
    }

    public void setAvgSpaceFreelistBlocks(Double avgSpaceFreelistBlocks) {
        this.avgSpaceFreelistBlocks = avgSpaceFreelistBlocks;
    }

    public Double getNumFreelistBlocks() {
        return numFreelistBlocks;
    }

    public void setNumFreelistBlocks(Double numFreelistBlocks) {
        this.numFreelistBlocks = numFreelistBlocks;
    }

    public Double getDegree() {
        return degree;
    }

    public void setDegree(Double degree) {
        this.degree = degree;
    }

    public Double getInstances() {
        return instances;
    }

    public void setInstances(Double instances) {
        this.instances = instances;
    }

    public String getCache() {
        return cache;
    }

    public void setCache(String cache) {
        this.cache = cache;
    }

    public String getTableLock() {
        return tableLock;
    }

    public void setTableLock(String tableLock) {
        this.tableLock = tableLock;
    }

    public Double getSampleSize() {
        return sampleSize;
    }

    public void setSampleSize(Double sampleSize) {
        this.sampleSize = sampleSize;
    }

    public Timestamp getLastAnalyzed() {
        return lastAnalyzed;
    }

    public void setLastAnalyzed(Timestamp lastAnalyzed) {
        this.lastAnalyzed = lastAnalyzed;
    }

    public String getPartitioned() {
        return partitioned;
    }

    public void setPartitioned(String partitioned) {
        this.partitioned = partitioned;
    }

    public String getIotType() {
        return iotType;
    }

    public void setIotType(String iotType) {
        this.iotType = iotType;
    }

    public String getTemporary() {
        return temporary;
    }

    public void setTemporary(String temporary) {
        this.temporary = temporary;
    }

    public String getSecondary() {
        return secondary;
    }

    public void setSecondary(String secondary) {
        this.secondary = secondary;
    }

    public String getNested() {
        return nested;
    }

    public void setNested(String nested) {
        this.nested = nested;
    }

    public String getBufferPool() {
        return bufferPool;
    }

    public void setBufferPool(String bufferPool) {
        this.bufferPool = bufferPool;
    }

    public String getFlushCache() {
        return flushCache;
    }

    public void setFlushCache(String flushCache) {
        this.flushCache = flushCache;
    }

    public String getCellFlashCache() {
        return cellFlashCache;
    }

    public void setCellFlashCache(String cellFlashCache) {
        this.cellFlashCache = cellFlashCache;
    }

    public String getRowMovement() {
        return rowMovement;
    }

    public void setRowMovement(String rowMovement) {
        this.rowMovement = rowMovement;
    }

    public String getGlobalStats() {
        return globalStats;
    }

    public void setGlobalStats(String globalStats) {
        this.globalStats = globalStats;
    }

    public String getUserStats() {
        return userStats;
    }

    public void setUserStats(String userStats) {
        this.userStats = userStats;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getSkipCorrupt() {
        return skipCorrupt;
    }

    public void setSkipCorrupt(String skipCorrupt) {
        this.skipCorrupt = skipCorrupt;
    }

    public String getMonitoring() {
        return monitoring;
    }

    public void setMonitoring(String monitoring) {
        this.monitoring = monitoring;
    }

    public String getClusterOwner() {
        return clusterOwner;
    }

    public void setClusterOwner(String clusterOwner) {
        this.clusterOwner = clusterOwner;
    }

    public String getDependencies() {
        return dependencies;
    }

    public void setDependencies(String dependencies) {
        this.dependencies = dependencies;
    }

    public String getCompression() {
        return compression;
    }

    public void setCompression(String compression) {
        this.compression = compression;
    }

    public String getCompressFor() {
        return compressFor;
    }

    public void setCompressFor(String compressFor) {
        this.compressFor = compressFor;
    }

    public String getDropped() {
        return dropped;
    }

    public void setDropped(String dropped) {
        this.dropped = dropped;
    }

    public String getReadOnly() {
        return readOnly;
    }

    public void setReadOnly(String readOnly) {
        this.readOnly = readOnly;
    }

    public String getSegmentCreated() {
        return segmentCreated;
    }

    public void setSegmentCreated(String segmentCreated) {
        this.segmentCreated = segmentCreated;
    }

    public String getResultCache() {
        return resultCache;
    }

    public void setResultCache(String resultCache) {
        this.resultCache = resultCache;
    }

    @Override
    public String getSchema() {
        return this.owner;
    }

    @Override
    public String getTabName() {
        return this.tableName;
    }

    @Override
    public String getTabType(){
        return "BASE TABLE";
    }
}

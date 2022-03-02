-- TODO rename in redis server
-- usage: redis-cli -h 10.10.201.100 -p 10401 EVAL "$(cat getXueqiuCommonsID.lua)" 1 XID:01:02
local arg = KEYS[1]
if (arg == nil or string.len(arg) < 9) then
    return -1
end
local prefix = string.sub(arg, 1, 4)
if (prefix ~= "KEY:") then
    return -2
end
-- project id must has 2 digits, ie. 02/08/11
local pid = tonumber(string.sub(arg, 5, 6))
if (pid > 15 or pid < 0) then
    return -3
end
-- instance id must has 2 digits, ie. 02/08/11
local cluster_id = tonumber(string.sub(arg, 8, 9))
if (cluster_id > 15 or cluster_id < 0) then
    return -4
end
local id_num = redis.call("INCR", "SNOWFLAKE_ID") % 65536
local sec = redis.call("TIME")[1] - 1646202889

-- lua version bigger then 5.3
-- return sec<<24 + pid<<20 + instance_id<<16 + sec
return sec*16777216 + pid*1048576 + cluster_id*65536 + id_num
